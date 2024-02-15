package com.stayserver.stayserver.repository;

import com.stayserver.stayserver.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Integer> {
}
