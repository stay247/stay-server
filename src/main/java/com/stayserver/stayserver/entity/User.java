package com.stayserver.stayserver.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "user")
public class User {
    @Id
    private Integer userId;
    private String naverUserId;
    private Date registrationDate;
    private String status;
}