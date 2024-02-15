package com.stayserver.stayserver.controller;

import com.stayserver.stayserver.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLConnection;

@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
@Slf4j
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/download/{fileName}")
    public ResponseEntity<byte[]> downloadItem(@PathVariable String fileName) {
        try {
            byte[] bytes = itemService.downloadItem(fileName);
            String mimeType = URLConnection.guessContentTypeFromName(fileName);
            mimeType = mimeType == null ? "application/octet-stream" : mimeType;

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(mimeType));
            headers.setContentDisposition(ContentDisposition.attachment().filename(fileName).build());

            return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            log.error("Error downloading file: {}", fileName, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadItem(@RequestParam("file") MultipartFile file) {
        try {
            String fileUrl = itemService.uploadItem(file);
            return ResponseEntity.ok(fileUrl);
        } catch (IOException e) {
            log.error("Error uploading file: {}", file.getOriginalFilename(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
