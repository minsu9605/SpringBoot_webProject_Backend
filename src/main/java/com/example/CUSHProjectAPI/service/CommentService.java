package com.example.CUSHProjectAPI.service;

import com.example.CUSHProjectAPI.dto.BoardCommentDto;
import com.example.CUSHProjectAPI.entity.BoardCommentEntity;
import com.example.CUSHProjectAPI.entity.BoardEntity;
import com.example.CUSHProjectAPI.entity.MemberEntity;
import com.example.CUSHProjectAPI.enums.Role;
import com.example.CUSHProjectAPI.repository.BoardCommentQueryRepository;
import com.example.CUSHProjectAPI.repository.BoardCommentRepository;
import com.example.CUSHProjectAPI.repository.BoardRepository;
import com.example.CUSHProjectAPI.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CommentService {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final BoardCommentRepository boardCommentRepository;
    private final BoardCommentQueryRepository boardCommentQueryRepository;


    /*댓글 등록*/
    public HashMap<String, Object> commentPost(Long bid, String comment, int cDepth, Long cGroup, String username) throws Exception {
        HashMap<String, Object> map = new HashMap<>();

        BoardCommentDto boardCommentDto = new BoardCommentDto();

        boardCommentDto.setComment(comment);
        boardCommentDto.setCDepth(cDepth);
        boardCommentDto.setCGroup(cGroup);
        boardCommentDto.setCreateDate(LocalDateTime.now());
        boardCommentDto.setUpdateDate(LocalDateTime.now());

        Optional<MemberEntity> memberEntity = memberRepository.findByUsername(username);
        Optional<BoardEntity> boardEntity = boardRepository.findById(bid);

        BoardCommentEntity boardCommentEntity = boardCommentDto.toEntity();

        boardCommentEntity.setCommentBoardId(boardEntity.get());
        boardCommentEntity.setCommentWriter(memberEntity.get());

        boardCommentRepository.save(boardCommentEntity);
        map.put("result","success");
        return map;
    }

    public HashMap<String, Object> getCommentList(Long bid) {
        HashMap<String, Object> map = new HashMap<>();
        Optional<BoardEntity> boardEntity = boardRepository.findById(bid);
        List<BoardCommentEntity> boardCommentEntityList = boardCommentQueryRepository.findByBoardId(boardEntity.get());

        List<BoardCommentDto> boardCommentDtoList = new ArrayList<>(); // 댓글 리스트
        List<Long> ccCountList = new ArrayList<>(); // 대댓글 갯수 카운트

        for (int i = 0; i < boardCommentEntityList.size(); i++) {
            boardCommentDtoList.add(boardCommentEntityList.get(i).toDto()); //댓글 리스트
            ccCountList.add(boardCommentQueryRepository.findReCommentCnt(boardCommentEntityList.get(i).getId())); //대댓글 갯수 카운트
        }
        map.put("list", boardCommentDtoList);
        map.put("commentCnt", ccCountList);

        return map;
    }

    /*댓글 삭제*/
    public Long deleteComment(Long cid, Object roleSession) {
        Optional<BoardCommentEntity> boardCommentEntity = boardCommentRepository.findById(cid);
        int depth = boardCommentEntity.get().getCDepth();
        Long reCommentCnt = boardCommentQueryRepository.deleteCheck(cid);

        if (depth == 0) {
            if (roleSession.equals(Role.ROLE_ADMIN)) {
                /*모댓글의 대댓글 까지 전부 삭제*/
                boardCommentQueryRepository.deleteByCid(cid);
                return 0L;
            }
            if (boardCommentQueryRepository.deleteCheck(cid) == 0) {
                boardCommentRepository.deleteById(cid);
            }
        } else if (depth == 1) {
            boardCommentRepository.deleteById(cid);
        }

        return reCommentCnt;
    }

    public HashMap<String, Object> modifyComment(Long cid, String comment) {
        HashMap<String, Object> map = new HashMap<>();
        boardCommentQueryRepository.updateComment(cid, comment);
        map.put("result","success");
        return map;
    }

    /*대댓글 목록*/
    public HashMap<String, Object> getReCommentList(Long cid) {
        List<BoardCommentEntity> boardCommentEntityList = boardCommentQueryRepository.findByCGroup(cid);
        List<BoardCommentDto> boardCommentDtoList = new ArrayList<>();

        for (int i = 0; i < boardCommentEntityList.size(); i++) {
            boardCommentDtoList.add(boardCommentEntityList.get(i).toDto());
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("list",boardCommentDtoList);
        return map;
    }
}
