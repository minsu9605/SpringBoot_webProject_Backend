package com.example.CUSHProjectAPI.repository;

import com.example.CUSHProjectAPI.entity.BoardCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardCategoryRepository extends JpaRepository<BoardCategoryEntity, Long> {
    Optional<BoardCategoryEntity> findById(Long id);
    Optional<BoardCategoryEntity> findByName(String name);
    List<BoardCategoryEntity> findAll();
}
