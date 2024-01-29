package com.stayserver.stayserver.dto.naverUser;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NaverUserDTO {
    private String resultcode;
    private String message;
    private NaverUser response;
}
