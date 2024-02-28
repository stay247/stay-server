package com.stayserver.stayserver.repository.jpa;

import com.stayserver.stayserver.entity.CollectionShare;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CollectionShareRepository extends JpaRepository<CollectionShare, Integer> {

    List<CollectionShare> findAllBySharedWithUserId(Integer sharedWithUserId);
}
