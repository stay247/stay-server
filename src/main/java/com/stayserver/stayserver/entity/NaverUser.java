package com.stayserver.stayserver.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Data
public class NaverUser {
    @Id
    private String id;
    private String nickname;
    private String profileImage;
    private String age;
    private char gender;
    private String email;
    private String mobile;
    private String mobile_e164;
    private String name;
    private String birthday;
    private Integer birthyear;

}
