package com.stayserver.stayserver.service;

import com.stayserver.stayserver.entity.NaverUser;
import com.stayserver.stayserver.entity.User;
import com.stayserver.stayserver.repository.NaverUserRepository;
import com.stayserver.stayserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private final NaverUserRepository naverUserRepository;
    private final UserRepository userRepository;

    @Transactional
    public void registerUser(NaverUser naverUser) {

        naverUserRepository.save(naverUser);

        User user = new User();
        user.setNaverUserId(naverUser.getId());
        user.setRegistrationDate(new Date());
        user.setStatus("normal");

        userRepository.save(user);

        // 해당 유저의 페이지로 이동하는 로직 추가해야함
    }
}
