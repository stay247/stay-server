package com.stayserver.stayserver.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Collection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer collectionId;
    private Integer userId;
    private String name;
    private String description;
    private String backgroundImageData;
    private Boolean sharable;
    private LocalDateTime createdAt;
    private String tag;

}
