package com.stayserver.stayserver.security.service;

import com.stayserver.stayserver.entity.KakaoUser;
import com.stayserver.stayserver.entity.User;
import com.stayserver.stayserver.exception.CustomOAuth2AuthenticationException;
import com.stayserver.stayserver.repository.KakaoUserRepository;
import com.stayserver.stayserver.repository.UserRepository;
import com.stayserver.stayserver.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class KakaoOAuth2UserInfoService implements OAuth2UserInfoService {

    private final KakaoUserRepository kakaoUserRepository;
    private final UserRepository userRepository;
    private final ItemService itemService;

    @Override
    public OAuth2User processOAuth2User(String oauthProvider, OAuth2User oAuth2User) {
        try {
            if (isUserRegistered(oAuth2User)) {

            } else {
                registerUser(oAuth2User, oauthProvider);
                // 로그인 처리 (jwt)

            }

        } catch (Exception e) {
            throw new CustomOAuth2AuthenticationException("An error occurred processing OAuth2User", e);
        }

        return oAuth2User;

    }

    @Override
    public boolean isUserRegistered(OAuth2User oAuth2User) throws Exception{
        try {
            Map<String, Object> attributes = oAuth2User.getAttributes();

            String id = attributes.get("id").toString();

            Optional<KakaoUser> kakaoUser = kakaoUserRepository.findById(id);

            if (kakaoUser.isPresent()) {
                // 해당 유저가 존재할 경우
                return true;
            } else {
                // 유저가 존재하지 않을 경우
                return false;
            }
        } catch (Exception e) {
            log.error("Failed to check User is registered {}: {}", oAuth2User, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void registerUser(OAuth2User oAuth2User, String registrationId) {
        try {
            Map<String, Object> attributes = oAuth2User.getAttributes();

            String id = attributes.get("id").toString();
            Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
            String nickname = properties.get("nickname").toString();

            // 추출한 정보를 사용해 NaverUser 객체 생성 및 저장
            KakaoUser kakaoUser = KakaoUser.builder()
                    .id(id)
                    .name(nickname)
                    .build();

            kakaoUserRepository.save(kakaoUser);

            User user = User.builder()
                    .oauthProvider("kakao")
                    .oauthId(id)
                    .status("normal")
                    .role("user")
                    .createdAt(LocalDateTime.now())
                    .build();

            userRepository.save(user);

            // 기본 아이템 세팅
            itemService.setDefaultData(user);
        } catch (Exception e) {
            log.error("Error registering user with response data {}: {}", oAuth2User, e.getMessage(), e);
            throw e;
        }
    }
}
