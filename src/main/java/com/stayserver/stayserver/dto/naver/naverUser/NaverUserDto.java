package com.stayserver.stayserver.dto.naver.naverUser;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NaverUserDto {
    private String id;
    private String nickname;
    private String profile_image;
    private String age;
    private String gender;
    private String email;
    private String mobile;
    private String mobile_e164;
    private String name;
    private String birthday;
    private String birthyear;
}
