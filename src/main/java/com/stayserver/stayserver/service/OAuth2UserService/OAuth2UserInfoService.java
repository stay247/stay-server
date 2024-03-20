package com.stayserver.stayserver.service.OAuth2UserService;

import org.springframework.security.oauth2.core.user.OAuth2User;

public interface OAuth2UserInfoService {
    OAuth2User processOAuth2User(String registrationId, OAuth2User oAuth2User) throws Exception;
}