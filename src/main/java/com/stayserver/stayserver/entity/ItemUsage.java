package com.stayserver.stayserver.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class ItemUsage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer itemUsageId;

    private Integer itemId;
    private Integer userId;
    private BigDecimal xCoordinate;
    private BigDecimal yCoordinate;
    private BigDecimal zCoordinate;

}
