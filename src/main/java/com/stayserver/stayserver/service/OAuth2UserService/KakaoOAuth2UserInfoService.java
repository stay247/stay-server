package com.stayserver.stayserver.service.OAuth2UserService;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class KakaoOAuth2UserInfoService implements OAuth2UserInfoService{
    @Override
    public OAuth2User processOAuth2User(String oauthProvider, OAuth2User oAuth2User) {
        return oAuth2User;
    }
}
