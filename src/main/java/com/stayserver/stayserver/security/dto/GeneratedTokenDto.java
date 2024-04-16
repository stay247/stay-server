package com.stayserver.stayserver.security.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class GeneratedTokenDto {

    private final String accessToken;
    private final String refreshToken;


}
