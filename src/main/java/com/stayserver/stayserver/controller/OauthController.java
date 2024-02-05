package com.stayserver.stayserver.controller;

import com.stayserver.stayserver.dto.naver.naverUser.NaverTokenDto;
import com.stayserver.stayserver.service.OauthService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login/naver")
public class OauthController {

    private final OauthService oauthService;

    @GetMapping("")
    public RedirectView naverLogin(HttpSession httpSession) {
        String redirectUrl = oauthService.createNaverOauthURL(httpSession);
        return new RedirectView(redirectUrl);
    }

    @GetMapping("/callback")
    public RedirectView naverCallback(HttpSession httpSession, @RequestParam("code") String code, @RequestParam("state") String state) {

        if (oauthService.verifyState(httpSession, state)) {
            NaverTokenDto naverToken = oauthService.getNaverToken(code);
            oauthService.getUserByNaverToken(naverToken);

            return new RedirectView(/* 로그인 이후 페이지 */"success");
        } else {
            return new RedirectView(/* 에러 페이지*/"error");
        }
    }
}
