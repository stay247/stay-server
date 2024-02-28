package com.stayserver.stayserver.controller;

import com.stayserver.stayserver.dto.CollectionDTO;
import com.stayserver.stayserver.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/item")
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/collections/{userId}")
    public ResponseEntity<List<CollectionDTO>> getUserCollections(@PathVariable("userId") Integer userId) {

        return ResponseEntity.ok(itemService.findCollectionsByUserId(userId));
    }
}
