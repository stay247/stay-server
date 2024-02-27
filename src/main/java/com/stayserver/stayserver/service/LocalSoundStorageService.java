package com.stayserver.stayserver.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
@Slf4j
public class LocalSoundStorageService implements SoundStorageService {

    private final Path rootLocation = Paths.get("src/main/resources/storage/media");

    @Override
    public Boolean saveSound(InputStream soundStream, String filename) {
        try {
            Files.copy(soundStream, this.rootLocation.resolve(filename));
            return true;
        } catch (Exception e) {
            log.error("Failed to store file {}: {}", filename, e.getMessage(), e);
            return false;
        }
    }

    @Override
    public Resource loadSound(String filename) {
        try {
            Path file = rootLocation.resolve(filename); // rootLocation은 파일이 위치한 디렉토리의 Path 객체
            Resource resource = new FileSystemResource(file); // FileSystemResource를 사용하여 Resource 생성
            if (!resource.exists()) {
                throw new RuntimeException("File not found " + filename);
            }
            return resource;
        } catch (Exception e) {
            log.error("Failed to load file {}: {}", filename, e.getMessage(), e);
            throw new RuntimeException("Failed to load file " + filename, e);
        }
    }

    @Override
    public void deleteSound(String filename) {
        try {
            Files.deleteIfExists(rootLocation.resolve(filename));
        } catch (Exception e) {
            log.error("Failed to delete file {}: {}", filename, e.getMessage(), e);
            throw new RuntimeException("Failed to delete file " + filename, e);
        }
    }
}
