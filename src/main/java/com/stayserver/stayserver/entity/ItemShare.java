package com.stayserver.stayserver.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class ItemShare {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer itemShareId;
    private Integer itemId;
    private Integer sharedWithUserId;
    private LocalDateTime sharedAt;


}
