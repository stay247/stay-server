package com.stayserver.stayserver.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "shape")
public class Shape {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer shapeId;

    private Integer itemId;
    private String shapeData;

}
