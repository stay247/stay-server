package com.stayserver.stayserver.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class LocalMediaStorageService implements MediaStorageService {

    private final Path rootLocation = Paths.get("src/main/resources/storage/media");

    @Override
    public String saveMedia(InputStream mediaStream, String filename) {
        try {
            Files.copy(mediaStream, this.rootLocation.resolve(filename));
            return filename;
        } catch (Exception e) {
            log.error("Failed to store file {}: {}", filename, e.getMessage(), e);
            throw new RuntimeException("Failed to store file " + filename, e);
        }
    }

    @Override
    public InputStream loadMedia(String filename) {
        try {
            Path file = rootLocation.resolve(filename);
            return Files.newInputStream(file);
        } catch (Exception e) {
            log.error("Failed to load file {}: {}", filename, e.getMessage(), e);
            throw new RuntimeException("Failed to load file " + filename, e);
        }
    }

    @Override
    public void deleteMedia(String filename) {
        try {
            Files.deleteIfExists(rootLocation.resolve(filename));
        } catch (Exception e) {
            log.error("Failed to delete file {}: {}", filename, e.getMessage(), e);
            throw new RuntimeException("Failed to delete file " + filename, e);
        }
    }
}
