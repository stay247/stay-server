package com.stayserver.stayserver.dto;

import lombok.*;

@NoArgsConstructor
@Getter
@ToString
@AllArgsConstructor
@Builder
public class SecurityUserDto {
    private Integer userIdx;
    private String nickname;
    private String role;


}
