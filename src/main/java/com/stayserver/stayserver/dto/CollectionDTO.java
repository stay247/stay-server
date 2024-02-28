package com.stayserver.stayserver.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CollectionDTO {

    private Integer collectionId;
    private Integer userId;
    private String name;
    private String description;
    private String backgroundImageData;
    private Boolean sharable;
    private String tag;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
