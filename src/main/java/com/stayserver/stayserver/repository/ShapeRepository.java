package com.stayserver.stayserver.repository;

import com.stayserver.stayserver.entity.Shape;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShapeRepository extends JpaRepository<Shape, Integer> {

    Optional<Shape> findByItemId(Integer itemId);

}
