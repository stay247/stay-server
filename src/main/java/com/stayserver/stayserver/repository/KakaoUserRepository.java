package com.stayserver.stayserver.repository;

import com.stayserver.stayserver.entity.KakaoUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KakaoUserRepository extends JpaRepository<KakaoUser, String> {
}
