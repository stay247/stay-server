package com.stayserver.stayserver.repository.jpa;

import com.stayserver.stayserver.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Integer> {
}
