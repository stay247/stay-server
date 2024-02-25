package com.stayserver.stayserver.dto;

import lombok.Setter;

import java.net.URL;
import java.util.List;

@Setter
public class ItemUrlsAccessTokenDto {
    private List<URL> itemUrls;
    private String accessToken;
}