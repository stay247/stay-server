package com.stayserver.stayserver.service.jwt;

import org.springframework.stereotype.Service;

@Service
public class RefreshTokenService {

    //Redis 작업
    public void saveTokenInfo(String refreshToken, String accessToken) {
    }
}
