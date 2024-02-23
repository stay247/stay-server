package com.stayserver.stayserver.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import com.stayserver.stayserver.entity.Item;
import com.stayserver.stayserver.entity.ItemUsage;
import com.stayserver.stayserver.entity.Shape;
import com.stayserver.stayserver.entity.Sound;
import com.stayserver.stayserver.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final AmazonS3Client amazonS3Client;
    private final NaverUserRepository naverUserRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final ShapeRepository shapeRepository;
    private final SoundRepository soundRepository;
    private final ItemUsageRepository itemUsageRepository;

    @Value("${cloud.aws.s3.bucket.audio}")
    private String audioBucket;

    @Value("${cloud.aws.s3.bucket.image}")
    private String imageBucket;


    // aws s3 파일 다운로드
    public byte[] downloadItem(String fileName) throws IOException {
        S3Object item = amazonS3Client.getObject(audioBucket, fileName);
        return IOUtils.toByteArray(item.getObjectContent());
    }

    // aws s3 파일 업로드
    public String uploadItem(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String fileUrl = "https://" + audioBucket + ".s3.amazonaws.com/" + fileName;
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        amazonS3Client.putObject(audioBucket, fileName, file.getInputStream(), metadata);
        return fileUrl;
    }

    // 특정 사용자에 알맞는 모든 아이템 링크 전달
    public List<URL> getUserItemLinks(String naverUserId) {
        // 사용자 ID를 기반으로 접근 가능한 아이템 목록 조회
        List<URL> urls = new ArrayList<>();

        userRepository.findByNaverUserId(naverUserId).ifPresent(user -> {
            List<ItemUsage> itemUsages = itemUsageRepository.findAllByUserId(user.getUserId());

            itemUsages.forEach(itemUsage -> {
                // 아이템 정보 조회
                Item item = itemRepository.findById(itemUsage.getItemId()).orElse(null);
                if (item != null) {

                    // 모양 정보 조회
                    Shape shape = shapeRepository.findByItemId(item.getItemId()).orElse(null);
                    if (shape != null) {
                        URL shapeLink = createPresignedS3Url(imageBucket, shape.getShapeData()); // 이미지 버킷 사용, Data = '파일명.확장자'
                        urls.add(shapeLink);
                    }

                    // 소리 정보 조회
                    Sound sound = soundRepository.findByItemId(item.getItemId()).orElse(null);
                    if (sound != null) {
                        URL soundLink = createPresignedS3Url(audioBucket, sound.getSoundData()); // 오디오 버킷 사용, Data = '파일명.확장자'
                        urls.add(soundLink);
                    }
                }
            });
        });
        return urls;
    }

    // 사전 서명된 URL 생성
    public URL createPresignedS3Url(String bucketName, String fileName) {
        // 만료 시간 설정
        Date expiration = new Date();
        long expireInMilliSeconds = 1000 * 60 * 60; // 1시간
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


}
