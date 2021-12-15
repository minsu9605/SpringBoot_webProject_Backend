package com.example.CUSHProjectAPI.repository;

import com.example.CUSHProjectAPI.dto.MemberDto;
import com.example.CUSHProjectAPI.entity.MemberEntity;
import com.example.CUSHProjectAPI.entity.QMemberEntity;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class MemberQueryRepository {
    private final JPAQueryFactory queryFactory;

    public MemberEntity findByUsername(String username) {
        return queryFactory.selectFrom(QMemberEntity.memberEntity)
                .where(QMemberEntity.memberEntity.username.eq(username))
                .fetchOne();
    }

    //본인것 제외한 아이디 찾기
    public QueryResults<String> findExistUsername(Long id) {
        return queryFactory.select(QMemberEntity.memberEntity.username)
                .from(QMemberEntity.memberEntity)
                .where(QMemberEntity.memberEntity.id.ne(id))
                .fetchResults();
    }

    //본인것 제외한 닉네임 찾기
    public QueryResults<String> findExistNickname(Long id) {
        return queryFactory.select(QMemberEntity.memberEntity.nickname)
                .from(QMemberEntity.memberEntity)
                .where(QMemberEntity.memberEntity.id.ne(id))
                .fetchResults();
    }

    /*회원정보 수정*/
    @Transactional
    public void updateMemberInfo(MemberDto memberDto) {
        queryFactory.update(QMemberEntity.memberEntity)
                .set(QMemberEntity.memberEntity.id, memberDto.getId())
                .set(QMemberEntity.memberEntity.username, memberDto.getUsername())
                .set(QMemberEntity.memberEntity.nickname, memberDto.getNickname())
                .set(QMemberEntity.memberEntity.birth, memberDto.getYear() + "-" + memberDto.getMonth()+ "-" + memberDto.getDay())
                .set(QMemberEntity.memberEntity.age, memberDto.calAge(memberDto.getYear() + memberDto.getMonth() + memberDto.getDay()))
                .set(QMemberEntity.memberEntity.gender, memberDto.getGender())
                .set(QMemberEntity.memberEntity.role, memberDto.getRole())
                .where(QMemberEntity.memberEntity.id.eq(memberDto.getId()))
                .execute();
    }

    @Transactional
    public void updateMemberPassword(MemberDto memberDto) {
        queryFactory.update(QMemberEntity.memberEntity)
                .set(QMemberEntity.memberEntity.password, memberDto.getPassword())
                .where(QMemberEntity.memberEntity.username.eq(memberDto.getUsername()))
                .execute();
    }

    /*조회된 리스트 전체 크기*/
    public Long getTotalCount(String searchType, String keyword){
        return queryFactory.selectFrom(QMemberEntity.memberEntity)
                .where(eqSearchType(searchType,keyword))
                .fetchCount();
    }

    /*한페이지 출력 리스트*/
    public List<MemberEntity> getMemberList(int page, int perPage, String searchType, String keyword){
        int start = (page * perPage) - perPage;
        return queryFactory.selectFrom(QMemberEntity.memberEntity)
                .where(eqSearchType(searchType,keyword))
                .orderBy(QMemberEntity.memberEntity.id.desc())
                .offset(start)
                .limit(perPage)
                .fetch();
    }

    /*검색조건 분기 함수*/
    private BooleanExpression eqSearchType(String searchType, String keyword){
        if(!keyword.equals("")){
            switch (searchType) {
                case "username":
                    return QMemberEntity.memberEntity.username.contains(keyword);
                case "nickname":
                    return QMemberEntity.memberEntity.nickname.contains(keyword);
            }
        }
        return null;
    }


}
