package com.stayserver.stayserver.controller;

import com.stayserver.stayserver.dto.ItemUrlDto;
import com.stayserver.stayserver.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
@Slf4j
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/user-items/{naverUserId}")
    public ResponseEntity<List<ItemUrlDto>> getUserItemUrls(@PathVariable String naverUserId) {
        try {
            List<ItemUrlDto> itemUrls = itemService.getUserItemUrls(naverUserId);
            if(itemUrls.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(itemUrls, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error retrieving item urls for user: {}", naverUserId, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



//    @GetMapping("/download/{fileName}")
//    public ResponseEntity<byte[]> downloadItem(@PathVariable String fileName) {
//        try {
//            byte[] bytes = itemService.downloadItem(fileName);
//            String mimeType = URLConnection.guessContentTypeFromName(fileName);
//            mimeType = mimeType == null ? "application/octet-stream" : mimeType;
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.parseMediaType(mimeType));
//            headers.setContentDisposition(ContentDisposition.attachment().filename(fileName).build());
//
//            return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
//        } catch (IOException e) {
//            log.error("Error downloading file: {}", fileName, e);
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @PostMapping("/upload")
//    public ResponseEntity<String> uploadItem(@RequestParam("file") MultipartFile file) {
//        try {
//            String fileUrl = itemService.uploadItem(file);
//            return ResponseEntity.ok(fileUrl);
//        } catch (IOException e) {
//            log.error("Error uploading file: {}", file.getOriginalFilename(), e);
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}
