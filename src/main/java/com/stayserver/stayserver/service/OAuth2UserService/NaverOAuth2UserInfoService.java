package com.stayserver.stayserver.service.OAuth2UserService;

import com.stayserver.stayserver.entity.NaverUser;
import com.stayserver.stayserver.entity.User;
import com.stayserver.stayserver.exception.CustomOAuth2AuthenticationException;
import com.stayserver.stayserver.repository.jpa.ItemShareRepository;
import com.stayserver.stayserver.repository.jpa.NaverUserRepository;
import com.stayserver.stayserver.repository.jpa.UserRepository;
import com.stayserver.stayserver.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class NaverOAuth2UserInfoService implements OAuth2UserInfoService {

    private final NaverUserRepository naverUserRepository;
    private final UserRepository userRepository;
    private final ItemService itemService;

    @Override
    public OAuth2User processOAuth2User(String oauthProvider, OAuth2User oAuth2User) {
        try {
            Map<String, Object> response = getResponse(oAuth2User);

            if (isUserRegistered(response)) {
                // 로그인 처리 (jwt)
            } else {
                registerUser(response);
                // 로그인 처리 (jwt)

            }
        } catch (Exception e) {
            throw new CustomOAuth2AuthenticationException("An error occurred processing OAuth2User", e);
        }

        return oAuth2User;
    }

    private static Map<String, Object> getResponse(OAuth2User oAuth2User) throws Exception {
        try {
            Map<String, Object> attributes = oAuth2User.getAttributes();

            // 'response' 내부의 Map을 추출
            Object response = attributes.get("response");
            if (response == null) {
                log.error("User attributes did not contain 'response' key.");
                throw new Exception("User attributes did not contain 'response' key.");
            }
            return (Map<String, Object>) response;

        } catch (Exception e) {
            log.error("An error occurred getting response from OAuth2User: {}", e.getMessage(), e);
            throw e;
        }

    }

    private boolean isUserRegistered(Map<String, Object> response) {
        try {
            String naverUserId = (String) response.get("id");

            Optional<NaverUser> naverUser = naverUserRepository.findById(naverUserId);

            if (naverUser.isPresent()) {
                // 해당 유저가 존재할 경우, true 반환
                return true;
            } else {
                // 유저가 존재하지 않을 경우, 회원가입 로직 실행 후 false 반환
                return false;
            }
        } catch (Exception e) {
            log.error("Failed to check User is registered {}: {}", response, e.getMessage(), e);
            throw e;
        }
    }

    private void registerUser(Map<String, Object> response) {
        try {
            // response Map에서 필요한 정보 추출
            String id = (String) response.get("id");
            String email = (String) response.get("email");
            String name = (String) response.get("name");
            String birthyear = (String) response.get("birthyear");

            // 추출한 정보를 사용해 NaverUser 객체 생성 및 저장
            NaverUser naverUser = NaverUser.builder()
                    .id(id)
                    .email(email)
                    .name(name)
                    .birthYear(birthyear)
                    .build();

            naverUserRepository.save(naverUser);

            User user = User.builder()
                    .oauthProvider("naver") // 이전 코드에서 naverUserId 변수가 정의되지 않았으므로, id를 사용
                    .oauthId(id)
                    .status("normal")
                    .createdAt(LocalDateTime.now())
                    .build();

            userRepository.save(user);

            // 기본 아이템 세팅
            itemService.setDefaultData(user);
        } catch (Exception e) {
            log.error("Error registering user with response data {}: {}", response, e.getMessage(), e);
            throw e;
        }
    }

}
