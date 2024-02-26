package com.stayserver.stayserver.repository.jpa;

import com.stayserver.stayserver.entity.Collection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectionRepository extends JpaRepository<Collection, Integer> {
}
