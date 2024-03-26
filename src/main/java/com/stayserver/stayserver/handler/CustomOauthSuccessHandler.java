package com.stayserver.stayserver.handler;

import com.stayserver.stayserver.entity.User;
import com.stayserver.stayserver.repository.jpa.GoogleUserRepository;
import com.stayserver.stayserver.repository.jpa.KakaoUserRepository;
import com.stayserver.stayserver.repository.jpa.NaverUserRepository;
import com.stayserver.stayserver.repository.jpa.UserRepository;
import com.stayserver.stayserver.util.JwtUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomOauthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final GoogleUserRepository googleUserRepository;
    private final NaverUserRepository naverUserRepository;
    private final KakaoUserRepository kakaoUserRepository;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        if (authentication instanceof OAuth2AuthenticationToken oauthToken) {

            // 클라이언트 등록 ID(즉, 프로바이더 식별자)를 얻습니다.
            String registrationId = oauthToken.getAuthorizedClientRegistrationId();

            // OAuth2User로 캐스팅하여 인증된 사용자 정보를 가져옵니다.
            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

            // 프로바이더별 사용자 정보 처리
            User user = processUserInformation(registrationId, oAuth2User);

            jwtUtil.generateToken(user.getUserId(), user.getRole());


            // TODO: user 사용하여 추가 작업 수행 (예: 토큰 생성)
        }
    }

    private User processUserInformation(String registrationId, OAuth2User oAuth2User) {
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String id; // 사용자 고유 식별 값

        switch (registrationId) {
            case "google":
                id = (String) attributes.get("sub");
                return findUserByProviderId(googleUserRepository, id, registrationId);

            case "naver":
                Map<String, Object> response = (Map<String, Object>) attributes.get("response");
                id = (String) response.get("id");
                return findUserByProviderId(naverUserRepository, id, registrationId);

            case "kakao":
                id = attributes.get("id").toString();
                return findUserByProviderId(kakaoUserRepository, id, registrationId);

            default:
                throw new OAuth2AuthenticationException("Unsupported provider: " + registrationId);
        }
    }

    private <T> User findUserByProviderId(JpaRepository<T, String> repository, String id, String registrationId) {
        T providerUser = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found in OauthProviderUserRepository" + " : " + registrationId + " -> " + id));

        User user = userRepository.findByOauthId(id).orElseThrow(() -> new EntityNotFoundException("User not found in UserRepository" + " : " + id));
        return user;
    }
}
