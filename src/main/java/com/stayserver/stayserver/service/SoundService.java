package com.stayserver.stayserver.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
@Slf4j
public class SoundService {

    private final SoundStorageService soundStorageService;

    public Resource getSound(String fileName) {
        return soundStorageService.loadSound(fileName);
    }

    public MediaType getContentType(String fileName) throws Exception {
        try {
            String mimeType = Files.probeContentType(Paths.get(fileName));
            if (mimeType == null) {
                return MediaType.APPLICATION_OCTET_STREAM; // 컨텐츠 타입을 알 수 없을 경우
            }
            return MediaType.parseMediaType(mimeType);
        } catch (Exception e) {
            log.error("Failed to determine MIME type for file: " + fileName, e);
            throw e;
        }
    }

    public void saveSound(MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot save empty file.");
        }

        String filename = file.getOriginalFilename();
        try (InputStream inputStream = file.getInputStream()) {
            soundStorageService.saveSound(inputStream, filename);
        } catch (Exception e) {
            log.error("Failed to store file {}: {}", filename, e.getMessage(), e);
            throw e;
        }
    }
}