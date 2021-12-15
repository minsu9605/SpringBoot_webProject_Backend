package com.example.CUSHProjectAPI.repository;

import com.example.CUSHProjectAPI.entity.NoticeBoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeBoardRepository extends JpaRepository<NoticeBoardEntity, Long> {


}
