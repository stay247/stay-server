package com.stayserver.stayserver.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import com.stayserver.stayserver.entity.User;
import com.stayserver.stayserver.repository.jpa.ItemRepository;
import com.stayserver.stayserver.repository.jpa.NaverUserRepository;
import com.stayserver.stayserver.repository.jpa.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final AmazonS3Client amazonS3Client;
    private final NaverUserRepository naverUserRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Value("${cloud.aws.s3.bucket.audio}")
    private String audioBucket;

    @Value("${cloud.aws.s3.bucket.image}")
    private String imageBucket;

    // 특정 사용자에 알맞는 모든 아이템 링크 전달

    // 사전 서명된 URL 생성
    public URL createPresignedS3Url(String bucketName, String fileName) {
        // 만료 시간 설정
        Date expiration = new Date();
        long expireInMilliSeconds = 600; // 10분
        long expTimeMillis = System.currentTimeMillis() + expireInMilliSeconds;
        expiration.setTime(expTimeMillis);

        // 사전 서명된 URL 요청 생성
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucketName, fileName)
                        .withMethod(HttpMethod.GET)
                        .withExpiration(expiration);

        // 사전 서명된 URL 생성
        return amazonS3Client.generatePresignedUrl(generatePresignedUrlRequest);
    }


    public void setDefaultData(User user) {

    }
}
