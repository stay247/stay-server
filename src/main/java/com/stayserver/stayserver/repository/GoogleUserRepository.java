package com.stayserver.stayserver.repository;

import com.stayserver.stayserver.entity.GoogleUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoogleUserRepository extends JpaRepository<GoogleUser, String> {
}
