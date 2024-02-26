package com.stayserver.stayserver.service;


import com.google.gson.Gson;
import com.stayserver.stayserver.dto.naver.NaverMsgDto;
import com.stayserver.stayserver.dto.naver.NaverTokenDto;
import com.stayserver.stayserver.dto.naver.NaverUserDto;
import com.stayserver.stayserver.entity.NaverUser;
import com.stayserver.stayserver.entity.User;
import com.stayserver.stayserver.repository.jpa.NaverUserRepository;
import com.stayserver.stayserver.repository.jpa.UserRepository;
import com.stayserver.stayserver.util.RestUtil;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OauthService {

    private final RestUtil restUtil;
    private final NaverUserRepository naverUserRepository;
    private final UserRepository userRepository;
    private final ItemService itemService;

    @Value("${naver.url.base}")
    private String naverUrlBase;

    @Value("${naver.client.id}")
    private String naverClientId;

    @Value("${naver.client.secret}")
    private String naverClientSecret;

    @Value("${naver.url.redirect}")
    private String naverUrlRedirect;

    @Value("${naver.url.userdata}")
    private String naverUrlUserData;


    public String createNaverOauthURL(HttpSession httpSession) {
        String state = generateState();
        httpSession.setAttribute("state", state);  // 세션에 상태 토큰 저장

        return UriComponentsBuilder.fromUriString(naverUrlBase + "authorize")
                .queryParam("client_id", naverClientId)
                .queryParam("response_type", "code")
                .queryParam("redirect_uri", naverUrlRedirect)
                .queryParam("state", state)
                .build()
                .encode(StandardCharsets.UTF_8)
                .toUriString();
    }

    public NaverTokenDto getNaverToken(String code) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", naverClientId);
        body.add("client_secret", naverClientSecret);
        body.add("grant_type", "authorization_code");
        body.add("code", code);

        String response = restUtil.post(naverUrlBase + "token", body);
        Gson gson = new Gson();

        return gson.fromJson(response, NaverTokenDto.class);
    }


    public void getUserByNaverToken(NaverTokenDto naverTokenDTO) {

        String accessToken = naverTokenDTO.getAccess_token();
        String tokenType = naverTokenDTO.getToken_type();

        String url = naverUrlUserData;

        String response = restUtil.get(url, accessToken, tokenType);

        Gson gson = new Gson();
        NaverMsgDto user = gson.fromJson(response, NaverMsgDto.class);

        if (user.getMessage().equals("success")) {
            isUserRegistered(user.getResponse());
        }

    }

    private void isUserRegistered(NaverUserDto naverUserDto) {
        String naverUserId = naverUserDto.getId();
        Optional<NaverUser> naverUser = naverUserRepository.findById(naverUserId);

        naverUser.ifPresentOrElse(
                foundUser -> {
                    // 해당 유저의 페이지로 이동하는 로직
                },
                () -> registerUser(naverUserDto) // 회원가입
        );
    }

    private void registerUser(NaverUserDto naverUserDto) {
        NaverUser naverUser = new NaverUser();
        naverUser.setId(naverUserDto.getId());
        naverUser.setNickname(naverUserDto.getNickname());
        naverUser.setProfileImage(naverUserDto.getProfile_image());
        naverUser.setAge(naverUserDto.getAge());
        naverUser.setGender(naverUserDto.getGender());
        naverUser.setEmail(naverUserDto.getEmail());
        naverUser.setMobile(naverUserDto.getMobile());
        naverUser.setMobileE164(naverUserDto.getMobile_e164());
        naverUser.setName(naverUserDto.getName());
        naverUser.setBirthDay(naverUserDto.getBirthday());
        naverUser.setBirthYear(naverUserDto.getBirthyear());
        naverUserRepository.save(naverUser);

        User user = new User();
        user.setNaverUserId(naverUser.getId());
        user.setCreatedAt(LocalDateTime.now());
        user.setStatus("normal");
        userRepository.save(user);

        // 기본 아이템 세팅
        itemService.setDefaultData(user);

        // 해당 유저의 페이지로 이동하는 로직
    }

    // CSRF 방지를 위한 상태 토큰 생성 코드
    // 상태 토큰은 추후 검증을 위해 세션에 저장되어야 한다.
    private String generateState() {
        SecureRandom random = new SecureRandom();

        return new BigInteger(130, random).toString(32);
    }

    public Boolean verifyState(HttpSession httpSession, String state) {
        // 세션 또는 별도의 저장 공간에서 상태 토큰을 가져옴
        String storedState = (String) httpSession.getAttribute("state");

        return state.equals(storedState);
    }
}
