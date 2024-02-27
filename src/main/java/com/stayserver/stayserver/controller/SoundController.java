package com.stayserver.stayserver.controller;

import com.stayserver.stayserver.service.SoundService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
public class SoundController {

    private final SoundService soundService;

    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> downloadSound(@PathVariable("fileName") String fileName) throws Exception {
        Resource fileResource = soundService.getSound(fileName);
        if (fileResource == null) {
            return ResponseEntity.notFound().build();
        }
        MediaType contentType = soundService.getContentType(fileName);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .contentType(contentType) // 적절한 Content-Type 설정
                .body(fileResource);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadSound(@RequestParam("file") MultipartFile file) throws Exception {
        soundService.saveSound(file);

        return ResponseEntity.ok("File uploaded successfully");
    }
}

