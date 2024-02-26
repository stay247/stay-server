package com.stayserver.stayserver.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class CollectionItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer collectionItemId;
    private Integer collectionId;
    private Integer itemId;
    private Integer order;
    private Integer volume;

}
