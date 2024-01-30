package com.stayserver.stayserver.service;


import com.google.gson.Gson;
import com.stayserver.stayserver.config.ApplicationEnvironmentConfig;
import com.stayserver.stayserver.dto.NaverTokenDto;
import com.stayserver.stayserver.dto.naverUser.NaverMsgDto;
import com.stayserver.stayserver.dto.naverUser.NaverUserDto;
import com.stayserver.stayserver.entity.NaverUser;
import com.stayserver.stayserver.mapper.NaverUserMapper;
import com.stayserver.stayserver.repository.NaverUserRepository;
import com.stayserver.stayserver.repository.UserRepository;
import com.stayserver.stayserver.util.RestUtil;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OauthService {

    private final ApplicationEnvironmentConfig envConfig;
    private final RestUtil restUtil;
    private final NaverUserRepository naverUserRepository;
    private final UserRepository userRepository;

    public String createNaverOauthURL(HttpSession httpSession) {

        String baseURL = envConfig.getConfigValue("naver.url.base") + "authorize";
        String clientID = envConfig.getConfigValue("naver.client.id");
        String redirectURL = envConfig.getConfigValue("naver.url.redirect");

        String state = generateState();
        httpSession.setAttribute("state", state);  // 세션에 상태 토큰 저장

        return UriComponentsBuilder.fromUriString(baseURL)
                .queryParam("client_id", clientID)
                .queryParam("response_type", "code")
                .queryParam("redirect_uri", redirectURL)
                .queryParam("state", state)
                .build()
                .encode(StandardCharsets.UTF_8)
                .toUriString();
    }

    public NaverTokenDto getNaverToken(String code) {
        String tokenUrl = envConfig.getConfigValue("naver.url.base") + "token";
        String clientID = envConfig.getConfigValue("naver.client.id");
        String clientSecret = envConfig.getConfigValue("naver.client.secret");

//        String state = generateState();

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", clientID);
        body.add("client_secret", clientSecret);
        body.add("grant_type", "authorization_code");
//        body.add("state", state); 사용 이유가 없음
        body.add("code", code);

        String response = restUtil.post(tokenUrl, body);
        Gson gson = new Gson();

        NaverTokenDto naverToken = gson.fromJson(response, NaverTokenDto.class);

        System.out.println(naverToken.getAccess_token());
        System.out.println(naverToken.getRefresh_token());
        System.out.println(naverToken.getToken_type());
        System.out.println(naverToken.getExpires_in());

        return naverToken;
    }


    public void getUserByNaverToken(NaverTokenDto naverTokenDTO) {

        String accessToken = naverTokenDTO.getAccess_token();
        String tokenType = naverTokenDTO.getToken_type();

        String url = envConfig.getConfigValue("naver.url.userdata");

        String response = restUtil.get(url, accessToken, tokenType);

        Gson gson = new Gson();
        NaverMsgDto user = gson.fromJson(response, NaverMsgDto.class);

        if (user.getMessage().equals("success")) {
            isUserRegistered(user.getResponse());
        }

    }

    public void isUserRegistered(NaverUserDto user) {
        String id = user.getId();
        Optional<NaverUser> naverUser = naverUserRepository.findById(id);

        if (naverUser.isPresent()) {
            // 해당 유저의 페이지로 이동
        } else {
            registerUser(user);
        }
    }

    private void registerUser(NaverUserDto user) {
        NaverUser naverUser = NaverUserMapper.INSTANCE.toEntity(user);

        naverUserRepository.save(naverUser);
        // 해당 유저의 페이지로 이동
    }


    // CSRF 방지를 위한 상태 토큰 생성 코드
    // 상태 토큰은 추후 검증을 위해 세션에 저장되어야 한다.
    public String generateState() {
        SecureRandom random = new SecureRandom();

        return new BigInteger(130, random).toString(32);
    }

    public Boolean verifyState(HttpSession httpSession, String state) {
        // 세션 또는 별도의 저장 공간에서 상태 토큰을 가져옴
        String storedState = (String) httpSession.getAttribute("state");

        return state.equals(storedState);
    }
}
