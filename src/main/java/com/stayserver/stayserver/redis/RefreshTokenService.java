package com.stayserver.stayserver.redis;

import com.stayserver.stayserver.security.dto.RefreshTokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;


    //Redis 작업
    @Transactional
    public void saveTokenInfo(Integer userIdx, String refreshToken, String accessToken) {
        refreshTokenRepository.save(new RefreshTokenDto(userIdx, refreshToken, accessToken));
    }

    @Transactional
    public void removeRefreshToken(String accessToken) {
        RefreshTokenDto refreshTokenDto = refreshTokenRepository.findByAccessToken(accessToken).orElseThrow(IllegalArgumentException::new);
        refreshTokenRepository.delete(refreshTokenDto);
    }
}
