package com.stayserver.stayserver.dto.naver.naverUser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class NaverTokenDto {
    private String access_token;
    private String refresh_token;
    private String token_type;
    private int expires_in;
}
