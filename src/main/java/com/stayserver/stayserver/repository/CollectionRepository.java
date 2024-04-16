package com.stayserver.stayserver.repository;

import com.stayserver.stayserver.entity.Collection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CollectionRepository extends JpaRepository<Collection, Integer> {

    public List<Collection> findByUserId(Integer userId);
}
