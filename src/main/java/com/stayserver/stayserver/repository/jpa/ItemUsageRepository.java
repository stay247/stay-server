package com.stayserver.stayserver.repository.jpa;

import com.stayserver.stayserver.entity.ItemUsage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemUsageRepository extends JpaRepository<ItemUsage, Integer> {

    List<ItemUsage> findAllByUserId(Integer userId);
}
