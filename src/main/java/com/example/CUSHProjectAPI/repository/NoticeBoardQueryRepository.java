package com.example.CUSHProjectAPI.repository;

import com.example.CUSHProjectAPI.dto.NoticeBoardListDto;
import com.example.CUSHProjectAPI.entity.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class NoticeBoardQueryRepository {
    private final JPAQueryFactory queryFactory;


    /*조회수 증가 쿼리*/
    @Transactional
    public void noticeHitUpdate(Long id){
        queryFactory.update(QNoticeBoardEntity.noticeBoardEntity)
                .set(QNoticeBoardEntity.noticeBoardEntity.hit, QNoticeBoardEntity.noticeBoardEntity.hit.add(1))
                .where(QNoticeBoardEntity.noticeBoardEntity.id.eq(id))
                .execute();
    }

    /*조회된 리스트 전체 크기*/
    public Long getTotalCount(String searchType, String keyword){
        return queryFactory.selectFrom(QNoticeBoardEntity.noticeBoardEntity)
                .where(eqSearchType(searchType,keyword))
                .fetchCount();
    }

    /*한페이지 출력 리스트*/
    public List<NoticeBoardEntity> getNoticeList(NoticeBoardListDto noticeBoardListDto){
        int start = (noticeBoardListDto.getPage() * noticeBoardListDto.getPerPage()) - noticeBoardListDto.getPerPage();
        return queryFactory.selectFrom(QNoticeBoardEntity.noticeBoardEntity)
                .where(eqSearchType(noticeBoardListDto.getSearchType(), noticeBoardListDto.getKeyword()))
                .orderBy(QNoticeBoardEntity.noticeBoardEntity.id.desc())
                .offset(start)
                .limit(noticeBoardListDto.getPerPage())
                .fetch();
    }

    /*검색조건 분기 함수*/
    private BooleanExpression eqSearchType(String searchType, String keyword){
        if(!keyword.equals("")){
            switch (searchType) {
                case "title":
                    return QNoticeBoardEntity.noticeBoardEntity.title.contains(keyword);
                case "content":
                    return QNoticeBoardEntity.noticeBoardEntity.content.contains(keyword);
            }
        }
        return null;
    }

}
