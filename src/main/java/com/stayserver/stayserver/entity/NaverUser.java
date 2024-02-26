package com.stayserver.stayserver.entity;
import jakarta.persistence.Column;
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
    private String gender;
    private String email;
    private String mobile;

    @Column(name = "mobile_e164")
    private String mobileE164;

    private String name;
    private String birthDay;
    private String birthYear;
}
