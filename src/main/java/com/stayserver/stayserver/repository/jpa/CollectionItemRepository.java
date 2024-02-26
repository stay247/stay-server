package com.stayserver.stayserver.repository.jpa;

import com.stayserver.stayserver.entity.CollectionItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectionItemRepository extends JpaRepository<CollectionItem, Integer> {
}
