package com.stayserver.stayserver.dto;

import lombok.Getter;
import lombok.Setter;

import java.net.URL;

@Getter
@Setter
public class ItemUrlDto {
    private String itemName;
    private URL shapeUrl;
    private URL soundUrl;

}

