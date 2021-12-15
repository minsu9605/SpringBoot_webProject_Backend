package com.example.CUSHProjectAPI.repository;


import com.example.CUSHProjectAPI.entity.BoardCommentEntity;
import com.example.CUSHProjectAPI.entity.BoardEntity;
import com.example.CUSHProjectAPI.entity.QBoardCommentEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class BoardCommentQueryRepository {
    private final JPAQueryFactory queryFactory;

    @Transactional
    public void updateComment(Long cid, String comment) {
        queryFactory.update(QBoardCommentEntity.boardCommentEntity)
                .set(QBoardCommentEntity.boardCommentEntity.comment, comment)
                .set(QBoardCommentEntity.boardCommentEntity.updateDate, LocalDateTime.now())
                .where(QBoardCommentEntity.boardCommentEntity.id.eq(cid))
                .execute();
    }

    /*대댓글 리스트*/
    public List<BoardCommentEntity> findByCGroup(Long cid) {
        return queryFactory.selectFrom(QBoardCommentEntity.boardCommentEntity)
                .where(QBoardCommentEntity.boardCommentEntity.cGroup.eq(cid)
                        .and(QBoardCommentEntity.boardCommentEntity.cDepth.eq(1))
                )
                .orderBy(QBoardCommentEntity.boardCommentEntity.id.asc())
                .fetch();
    }

    /*댓글 리스트*/
    public List<BoardCommentEntity> findByBoardId(BoardEntity boardEntity) {
        return queryFactory.selectFrom(QBoardCommentEntity.boardCommentEntity)
                .where(QBoardCommentEntity.boardCommentEntity.boardId.eq(boardEntity)
                        .and(QBoardCommentEntity.boardCommentEntity.cDepth.eq(0))
                )
                .orderBy(QBoardCommentEntity.boardCommentEntity.id.asc())
                .fetch();
    }

    public Long deleteCheck(Long cid) {
        return queryFactory.selectFrom(QBoardCommentEntity.boardCommentEntity)
                .where(QBoardCommentEntity.boardCommentEntity.cGroup.eq(cid))
                .fetchCount();
    }

    public Long findReCommentCnt(Long cid) {
        return queryFactory.selectFrom(QBoardCommentEntity.boardCommentEntity)
                .where(QBoardCommentEntity.boardCommentEntity.cGroup.eq(cid))
                .fetchCount();
    }

    /*모댓글, 대댓글 삭제*/
    @Transactional
    public void deleteByCid(Long cid) {
        queryFactory.delete(QBoardCommentEntity.boardCommentEntity)
                .where(QBoardCommentEntity.boardCommentEntity.id.eq(cid)
                        .or(QBoardCommentEntity.boardCommentEntity.cGroup.eq(cid))
                )
                .execute();
    }

    /*보드 id로 삭제(회원탈퇴 시 사용)*/
    @Transactional
    public void deleteByBoardId(BoardEntity boardEntity) {
        queryFactory.delete(QBoardCommentEntity.boardCommentEntity)
                .where(QBoardCommentEntity.boardCommentEntity.boardId.eq(boardEntity)
                )
                .execute();
    }
}
