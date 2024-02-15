package com.stayserver.stayserver.repository;

import com.stayserver.stayserver.entity.Sound;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SoundRepository extends JpaRepository<Sound, Integer> {
    Optional<Sound> findByItemId(Integer itemId);
}
