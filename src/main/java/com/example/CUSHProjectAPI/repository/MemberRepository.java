package com.example.CUSHProjectAPI.repository;

import com.example.CUSHProjectAPI.entity.MemberEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    Optional<MemberEntity> findByUsername(String username);
    List<MemberEntity> findAll();
    Page<MemberEntity> findByUsernameContaining(String keyword, Pageable pageable);

    //중복가입
    boolean existsByUsername(String username);
    boolean existsByNickname(String nickname);
}
