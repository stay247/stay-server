package com.stayserver.stayserver.security.dto;

import org.springframework.data.annotation.Id; // redis @Id
//import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@RedisHash(value = "jwtRefreshToken", timeToLive = 60 * 60 * 24 * 14)
public class RefreshTokenDto implements Serializable {

    @Id
    private Integer userIdx;

    @Indexed
    private String accessToken;

    private String refreshToken;

    public void updateAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
