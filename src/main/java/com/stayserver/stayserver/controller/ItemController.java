package com.stayserver.stayserver.controller;

import com.stayserver.stayserver.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;

@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/test")
    public ResponseEntity<Resource> getTest() {
        String filename = "fire.mp3";
        InputStream file = itemService.getMediaTest(filename); // 파일 로드 로직

        InputStreamResource resource = new InputStreamResource(file);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .contentType(MediaType.valueOf("audio/mpeg")) // 적절한 Content-Type 설정
                .body(resource);
    }
}
