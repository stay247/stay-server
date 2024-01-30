package com.stayserver.stayserver.repository;


import com.stayserver.stayserver.entity.NaverUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NaverUserRepository extends JpaRepository<NaverUser, String> {
}
