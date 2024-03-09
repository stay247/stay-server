package com.stayserver.stayserver.service.OAuth2UserService;

import com.stayserver.stayserver.entity.NaverUser;
import com.stayserver.stayserver.entity.User;
import com.stayserver.stayserver.repository.jpa.NaverUserRepository;
import com.stayserver.stayserver.repository.jpa.UserRepository;
import com.stayserver.stayserver.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final GoogleOAuth2UserInfoService googleOAuth2UserInfoService;
    private final NaverOAuth2UserInfoService naverOAuth2UserInfoService;
    private final KakaoOAuth2UserInfoService kakaoOAuth2UserInfoService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        try {
            OAuth2User oAuth2User = super.loadUser(userRequest);
            String registrationId = userRequest.getClientRegistration().getRegistrationId();

            switch (registrationId) {
                case "google":
                    return googleOAuth2UserInfoService.processOAuth2User(registrationId, oAuth2User);
                case "naver":
                    return naverOAuth2UserInfoService.processOAuth2User(registrationId, oAuth2User);
                case "kakao":
                    return kakaoOAuth2UserInfoService.processOAuth2User(registrationId, oAuth2User);
                default:
                    throw new OAuth2AuthenticationException("Unsupported provider: " + registrationId);
            }
        } catch (Exception e) {
            log.error("Error processing OAuth2 user from provider '{}': {}", userRequest.getClientRegistration().getRegistrationId(), e.toString());
            throw e;
        }

    }

//    private void isUserRegistered(OAuth2User oAuth2User) {
//        Map<String, Object> attributes = oAuth2User.getAttributes();
//
//        // 'response' 내부의 Map을 추출
//        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
//
//        if (response != null) {
//            String naverUserId = (String) response.get("id");
//
//            Optional<NaverUser> naverUser = naverUserRepository.findById(naverUserId);
//
//            naverUser.ifPresentOrElse(
//                    foundUser -> {
//                        // 해당 유저의 페이지로 이동하는 로직
//                        // 예: 리디렉션 로직 구현
//                    },
//                    () -> {
//                        // 회원가입 로직
//                        registerUser(response); // Map<String, Object> 타입의 response를 전달
//                    }
//            );
//        } else {
//            // 'response'가 null인 경우의 처리
//            // 예: 로깅, 에러 페이지로 리디렉션 등
//        }
//    }
//
//    private void registerUser(Map<String, Object> response) {
//        // response Map에서 필요한 정보 추출
//        String id = (String) response.get("id");
//        String email = (String) response.get("email");
//        String name = (String) response.get("name");
//        String birthyear = (String) response.get("birthyear");
//
//        // 추출한 정보를 사용해 NaverUser 객체 생성 및 저장
//        NaverUser naverUser = NaverUser.builder()
//                .id(id)
//                .email(email)
//                .name(name)
//                .birthYear(birthyear)
//                .build();
//
//        naverUserRepository.save(naverUser);
//
//        User user = User.builder()
//                .naverUserId(id) // 이전 코드에서 naverUserId 변수가 정의되지 않았으므로, id를 사용
//                .status("normal")
//                .createdAt(LocalDateTime.now())
//                .build();
//
//        userRepository.save(user);
//
//        // 기본 아이템 세팅
//        itemService.setDefaultData(user);
//    }



}

