package com.example.CUSHProjectAPI.repository;

import com.example.CUSHProjectAPI.entity.BoardCommentEntity;
import com.example.CUSHProjectAPI.entity.BoardEntity;
import com.example.CUSHProjectAPI.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardCommentRepository extends JpaRepository<BoardCommentEntity, Long> {
    List<BoardCommentEntity> findByBoardId(BoardEntity boardEntity);
    List<BoardCommentEntity> findByWriter(MemberEntity memberEntity);

    boolean existsByBoardId(BoardEntity boardEntity);
    boolean existsByWriter(MemberEntity memberEntity);

}
