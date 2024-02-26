package com.stayserver.stayserver.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer itemId;
    private Integer userId;
    private String name;
    private String description;
    private String iconData;
    private String soundData;
    private Boolean sharable;
    private String tag;
}



