package com.stayserver.stayserver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class TestFrontController {

    @GetMapping("/test")
    public String testPage() {
        return "/test.html";  // Thymeleaf 템플릿 'test.html'을 렌더링
    }
}
