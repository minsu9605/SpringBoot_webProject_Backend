package com.example.CUSHProjectAPI.repository;

import com.example.CUSHProjectAPI.entity.*;
import com.example.CUSHProjectAPI.enums.Status;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class BoardQueryRepository{
    private final JPAQueryFactory queryFactory;

    /*조회수 증가 쿼리*/
    @Transactional
    public void boardHitUpdate(Long id){
        queryFactory.update(QBoardEntity.boardEntity)
                .set(QBoardEntity.boardEntity.hit, QBoardEntity.boardEntity.hit.add(1))
                .where(QBoardEntity.boardEntity.id.eq(id))
                .execute();
    }

    /*판매완료 버튼*/
    @Transactional
    public void boardStatusUpdate(Long id){
        queryFactory.update(QBoardEntity.boardEntity)
                .set(QBoardEntity.boardEntity.status, Status.soldOut)
                .where(QBoardEntity.boardEntity.id.eq(id))
                .execute();
    }

    /*조회된 리스트 전체 크기*/
    public Long getTotalCount(BoardCategoryEntity boardCategoryEntity,String searchType, String keyword){
        return queryFactory.selectFrom(QBoardEntity.boardEntity)
                .where(QBoardEntity.boardEntity.category.eq(boardCategoryEntity)
                        .and(eqSearchType(searchType,keyword))
                )
                .fetchCount();
    }

    /*내가 쓴글 조회된 리스트 전체 크기*/
    public Long getMyBoardTotalCount(MemberEntity memberEntity,String searchType, String keyword){
        return queryFactory.selectFrom(QBoardEntity.boardEntity)
                .where(QBoardEntity.boardEntity.writer.eq(memberEntity)
                        .and(eqSearchType(searchType,keyword)))
                .fetchCount();
    }

    /*내가 쓴글 조회된 리스트 전체 크기*/
    public Long getMyOldBoardTotalCount(MemberEntity memberEntity,String searchType, String keyword){
        return queryFactory.selectFrom(QBoardEntity.boardEntity)
                .where(QBoardEntity.boardEntity.writer.eq(memberEntity)
                        .and(QBoardEntity.boardEntity.status.eq(Status.old))
                        .and(eqSearchType(searchType,keyword)))
                .fetchCount();
    }
    /*내가 쓴글 한페이지 출력 리스트*/
    public List<BoardEntity> getMyOldBoardList(MemberEntity memberEntity, int page, int perPage, String searchType, String keyword){
        int start = (page * perPage) - perPage;
        return queryFactory.selectFrom(QBoardEntity.boardEntity)
                .where(QBoardEntity.boardEntity.writer.eq(memberEntity)
                        .and(QBoardEntity.boardEntity.status.eq(Status.old))
                        .and(eqSearchType(searchType,keyword))
                )
                .orderBy(QBoardEntity.boardEntity.id.desc())
                .offset(start)
                .limit(perPage)
                .fetch();
    }

    public List<BoardEntity> getMyOldBoardAlertList(MemberEntity memberEntity,int startIndex, int searchStep){
        return queryFactory.selectFrom(QBoardEntity.boardEntity)
                .where(QBoardEntity.boardEntity.writer.eq(memberEntity)
                        .and(QBoardEntity.boardEntity.alertRead.eq(0))
                        .and(QBoardEntity.boardEntity.status.eq(Status.old))
                )
                .orderBy(QBoardEntity.boardEntity.updatedDate.asc())
                .offset(startIndex)
                .limit(searchStep)
                .fetch();
    }

    public Long getMyOldBoardAlertTotalCount(MemberEntity memberEntity){
        return queryFactory.selectFrom(QBoardEntity.boardEntity)
                .where(QBoardEntity.boardEntity.writer.eq(memberEntity)
                        .and(QBoardEntity.boardEntity.status.eq(Status.old))
                        .and(QBoardEntity.boardEntity.alertRead.eq(0))
                )
                .fetchCount();
    }

    @Transactional
    public void setAlertReading(Long id){
        queryFactory.update(QBoardEntity.boardEntity)
                .set(QBoardEntity.boardEntity.alertRead, 1)
                .where(QBoardEntity.boardEntity.id.eq(id))
                .execute();
    }

    /*내가 쓴글 한페이지 출력 리스트*/
    public List<BoardEntity> getMyBoardList(MemberEntity memberEntity, int page, int perPage, String searchType, String keyword){
        int start = (page * perPage) - perPage;
        return queryFactory.selectFrom(QBoardEntity.boardEntity)
                .where(QBoardEntity.boardEntity.writer.eq(memberEntity)
                        .and(eqSearchType(searchType,keyword))
                )
                .orderBy(QBoardEntity.boardEntity.id.desc())
                .offset(start)
                .limit(perPage)
                .fetch();
    }

    /*한페이지 출력 리스트*/
    public List<BoardEntity> getBoardList(BoardCategoryEntity boardCategoryEntity, int page, int perPage, String searchType, String keyword){
        int start = (page * perPage) - perPage;
        return queryFactory.selectFrom(QBoardEntity.boardEntity)
                .where(QBoardEntity.boardEntity.category.eq(boardCategoryEntity)
                    .and(eqSearchType(searchType,keyword))
                )
                .orderBy(QBoardEntity.boardEntity.id.desc())
                .offset(start)
                .limit(perPage)
                .fetch();
    }

    /*검색조건 분기 함수*/
    private BooleanExpression eqSearchType(String searchType, String keyword){
        if(!keyword.equals("")){
            switch (searchType) {
                case "title":
                    return QBoardEntity.boardEntity.title.contains(keyword);
                case "content":
                    return QBoardEntity.boardEntity.content.contains(keyword);
                case "writer":
                    return QBoardEntity.boardEntity.writer.nickname.eq(keyword);
            }
        }
        return null;
    }

    /*한페이지 출력 리스트*/
    public List<BoardEntity> getMapList(double startLat, double startLng, double endLat, double endLng){
        return queryFactory.selectFrom(QBoardEntity.boardEntity)
                .where(QBoardEntity.boardEntity.status.ne(Status.soldOut)
                        .and(QBoardEntity.boardEntity.myLat.between(startLat,endLat))
                        .and(QBoardEntity.boardEntity.myLng.between(startLng,endLng))
                )
                .orderBy(QBoardEntity.boardEntity.id.desc())
                .fetch();
    }
}
