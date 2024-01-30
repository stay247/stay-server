package com.stayserver.stayserver.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class User {
    @Id
    private Integer userId;
    private String naverUserId;
    private Date registrationDate;
    private String status;
}