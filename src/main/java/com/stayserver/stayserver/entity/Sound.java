package com.stayserver.stayserver.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Sound {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer soundId;

    private Integer itemId;
    private String soundData;

}
