package com.stayserver.stayserver.security.service;

import org.springframework.security.oauth2.core.user.OAuth2User;

public interface OAuth2UserInfoService {
    OAuth2User processOAuth2User(String oauthProvider, OAuth2User oAuth2User) throws Exception;

    boolean isUserRegistered(OAuth2User oAuth2User) throws Exception;

    void registerUser(OAuth2User oAuth2User, String registrationId) throws Exception;
}
