package com.stayserver.stayserver.controller;

import com.stayserver.stayserver.entity.Item;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequiredArgsConstructor
public class ItemController {

    @GetMapping("/{userIdx}/item")
    public Item getItem() {
    }
}

//https://stay-audio.s3.ap-northeast-2.amazonaws.com/sample.mp3
