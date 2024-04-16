package com.stayserver.stayserver.security.filter;

import com.stayserver.stayserver.dto.SecurityUserDto;
import com.stayserver.stayserver.entity.User;
import com.stayserver.stayserver.repository.UserRepository;
import com.stayserver.stayserver.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getRequestURI().contains("token/");
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // request Header에서 AccessToken을 가져온다.
        String accessToken = request.getHeader("Authorization");

        // 토큰 검사 생략(모두 허용 URL의 경우 토큰 검사 통과)
        if (!StringUtils.hasText(accessToken)) {
            doFilter(request, response, filterChain);
            return;
        }

        // AccessToken을 검증하고, 만료되었을경우 예외를 발생시킨다.
        if (!jwtUtil.verifyToken(accessToken)) {
            throw new JwtException("Access Token 만료!");
        }

        // AccessToken의 값이 있고, 유효한 경우에 진행한다.
        if (jwtUtil.verifyToken(accessToken)) {

            User user = userRepository.findById(jwtUtil.getUserIdx(accessToken))
                    .orElseThrow(IllegalStateException::new);

            // SecurityContext에 등록할 User 객체를 만들어준다.
            SecurityUserDto securityUserDto = SecurityUserDto.builder()
                    .userIdx(user.getUserId())
                    .role(user.getRole())
                    .build();

            // SecurityContext에 인증 객체를 등록해준다.
            Authentication auth = getAuthentication(securityUserDto);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }


    public Authentication getAuthentication(SecurityUserDto securityUserDto) {
        return new UsernamePasswordAuthenticationToken(securityUserDto, "", List.of(new SimpleGrantedAuthority(securityUserDto.getRole())));
    }

}
