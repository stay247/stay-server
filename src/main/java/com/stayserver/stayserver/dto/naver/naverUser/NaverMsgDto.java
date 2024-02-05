package com.stayserver.stayserver.dto.naver.naverUser;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NaverMsgDto {
    private String resultcode;
    private String message;
    private NaverUserDto response;
}
