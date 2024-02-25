package com.stayserver.stayserver.dto.naver;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NaverMsgDto {
    private String resultcode;
    private String message;
    private NaverUserDto response;
}
