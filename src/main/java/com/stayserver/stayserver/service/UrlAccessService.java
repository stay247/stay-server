package com.stayserver.stayserver.service;

import com.stayserver.stayserver.dto.ItemUrlDto;
import com.stayserver.stayserver.redis.UrlAccessToken;
import com.stayserver.stayserver.repository.redis.UrlAccessTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UrlAccessService {

    private final UrlAccessTokenRepository urlAccessTokenRepository;

    public void createUrlAccessToken(List<ItemUrlDto> itemUrls) {

        List<UrlAccessToken> urlAccessTokens = new ArrayList<>();

        for (ItemUrlDto itemUrl : itemUrls) {
            String itemName = itemUrl.getItemName();

            URL shapeUrl = itemUrl.getShapeUrl();
            URL soundUrl = itemUrl.getSoundUrl();

            UrlAccessToken shapeToken = new UrlAccessToken();
            UrlAccessToken soundToken = new UrlAccessToken();

            shapeToken.setUrl(shapeUrl);
            soundToken.setUrl(soundUrl);

            urlAccessTokens.add(shapeToken);
            urlAccessTokens.add(soundToken);

            urlAccessTokenRepository.save(shapeToken);
            urlAccessTokenRepository.save(soundToken);
        }
    }
}
