package com.stayserver.stayserver.repository;

import com.stayserver.stayserver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
