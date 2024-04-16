package com.stayserver.stayserver.repository;

import com.stayserver.stayserver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByOauthId(String oauthId);
}
