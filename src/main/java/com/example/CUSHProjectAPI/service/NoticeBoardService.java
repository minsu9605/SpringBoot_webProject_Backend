package com.example.CUSHProjectAPI.service;

import com.example.CUSHProjectAPI.dto.NoticeBoardDto;
import com.example.CUSHProjectAPI.dto.NoticeBoardListDto;
import com.example.CUSHProjectAPI.entity.MemberEntity;
import com.example.CUSHProjectAPI.entity.NoticeBoardEntity;
import com.example.CUSHProjectAPI.repository.MemberRepository;
import com.example.CUSHProjectAPI.repository.NoticeBoardQueryRepository;
import com.example.CUSHProjectAPI.repository.NoticeBoardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class NoticeBoardService {

    private final NoticeBoardRepository noticeBoardRepository;
    private final MemberRepository memberRepository;
    private final NoticeBoardQueryRepository noticeBoardQueryRepository;

    public List<NoticeBoardDto> getNoticeBoardList(NoticeBoardListDto noticeBoardListDto) {
        List<NoticeBoardEntity> noticeBoardEntityList = noticeBoardQueryRepository.getNoticeList(noticeBoardListDto);
        List<NoticeBoardDto> noticeBoardDtoList = new ArrayList<>();

        for (NoticeBoardEntity noticeBoardEntity : noticeBoardEntityList) {
            noticeBoardDtoList.add(noticeBoardEntity.toDto());
        }
        return noticeBoardDtoList;
    }

    /*조회된 전체 데이터 수*/
    public int getTotalSize(String searchType, String keyword) {
        return Math.toIntExact(noticeBoardQueryRepository.getTotalCount(searchType, keyword));
    }

    /*글 작성 후 전송*/
    public NoticeBoardDto noticeBoardWrite(NoticeBoardDto noticeBoardDto, String username, HttpServletRequest request) {
        Optional<MemberEntity> memberEntity = memberRepository.findByUsername(username);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        noticeBoardDto.setCreatedDate(LocalDateTime.now().format(formatter));
        noticeBoardDto.setUpdatedDate(LocalDateTime.now().format(formatter));
        /*ip찾는 로직*/
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null) {
            ip = request.getRemoteAddr();
        }
        noticeBoardDto.setWriteIp(ip);

        NoticeBoardEntity noticeBoardEntity = noticeBoardDto.toEntity();

        noticeBoardEntity.setWriter(memberEntity.get());

        return noticeBoardRepository.save(noticeBoardEntity).toDto();
    }

    /*글 수정 후 전송*/
    public NoticeBoardDto boardModifySave(NoticeBoardDto noticeBoardDto, String username) {
        Optional<MemberEntity> memberEntity = memberRepository.findByUsername(username);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        noticeBoardDto.setUpdatedDate(LocalDateTime.now().format(formatter));

        NoticeBoardEntity noticeBoardEntity = noticeBoardDto.toEntity();

        noticeBoardEntity.setWriter(memberEntity.get());

        return noticeBoardRepository.save(noticeBoardEntity).toDto();
    }

    /*조회수 증가*/
    public void noticeBoardHitUpdate(Long id) {
        noticeBoardQueryRepository.noticeHitUpdate(id);
    }

    //보드 글 상세보기
    public NoticeBoardDto noticeBoardContent(Long id) {
        NoticeBoardEntity noticeBoardEntity;
        if (id == null) {
            noticeBoardEntity = new NoticeBoardEntity();
        } else {
            noticeBoardEntity = noticeBoardRepository.findById(id).orElse(null);
        }
        return noticeBoardEntity.toDto();
    }

    /*게시글 삭제*/
    public void noticeBoardDelete(Long id) {
        noticeBoardRepository.deleteById(id);
    }

}
