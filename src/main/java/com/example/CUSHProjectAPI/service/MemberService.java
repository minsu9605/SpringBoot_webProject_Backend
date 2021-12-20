package com.example.CUSHProjectAPI.service;

import com.example.CUSHProjectAPI.dto.MemberDto;
import com.example.CUSHProjectAPI.entity.BoardCommentEntity;
import com.example.CUSHProjectAPI.entity.BoardEntity;
import com.example.CUSHProjectAPI.entity.MemberEntity;
import com.example.CUSHProjectAPI.enums.Role;
import com.example.CUSHProjectAPI.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberQueryRepository memberQueryRepository;
    private final BoardRepository boardRepository;
    private final BoardCommentRepository boardCommentRepository;
    private final BoardCommentQueryRepository boardCommentQueryRepository;


   /* @Transactional
    public Long singUp(MemberDto memberDto) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));
        memberDto.setRole(Role.ROLE_MEMBER);

        return memberRepository.save(memberDto.toEntity()).getId();
    }*/

    public MemberDto getMemberByUsername(String email) {
        MemberEntity memberEntity = memberQueryRepository.findByUsername(email);
        return memberEntity.toDto();
    }

    public MemberDto findById(Long id) {
        Optional<MemberEntity> memberEntity = memberRepository.findById(id);
        return memberEntity.get().toDto();
    }

    public void memberUpdate(MemberDto memberDto) {
        memberQueryRepository.updateMemberInfo(memberDto);
    }

    /*//패스워드 변경 전 기존 패스워드 검사
    public HashMap<String, Object> pwCheck(Authentication authentication, String original_Pw) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String db_Pw = getMemberByUsername(authentication.getName()).getPassword();
        HashMap<String, Object> map = new HashMap<>();
        map.put("result", passwordEncoder.matches(original_Pw, db_Pw));
        return map;
    }

    //패스워드 변경
    public void passwordUpdate(MemberDto memberDto) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));
        memberQueryRepository.updateMemberPassword(memberDto);
    }*/

    //멤버 탈퇴
    public void deleteUser(Long id) {
        Optional<MemberEntity> memberEntity = memberRepository.findById(id);

        //해당 회원이 작성한 게시물이 있을 때
        if(boardRepository.existsByWriter(memberEntity.get())){
            List<BoardEntity> boardEntity = boardRepository.findByWriter(memberEntity.get());
            for(int i=0; i<boardEntity.size(); i++){
                //해당게시물에 댓글이 있을 때 댓글 모두 삭제
                if(boardCommentRepository.existsByBoardId(boardEntity.get(i))){
                    boardCommentQueryRepository.deleteByBoardId(boardEntity.get(i));
                }
                //해당 게시물 삭제
                boardRepository.deleteById(boardEntity.get(i).getId());
            }
        }

        //해당 회원이 작성한 댓글 삭제
        if(boardCommentRepository.existsByWriter(memberEntity.get())){
            List<BoardCommentEntity> boardCommentEntityList = boardCommentRepository.findByWriter(memberEntity.get());
            for(int i=0; i<boardCommentEntityList.size();i++){
                boardCommentQueryRepository.deleteByCid(boardCommentEntityList.get(i).getId());
            }
        }

        memberRepository.deleteById(id);
    }

    //email 중복 검사
    public HashMap<String, Object> usernameOverlap(String username) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("result", memberRepository.existsByUsername(username));
        return map;
    }

    //닉네임 중복 검사
    public HashMap<String, Object> nicknameOverlap(String nickname) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("result", memberRepository.existsByNickname(nickname));
        return map;
    }

    //아이디 수정 중복 검사
    public HashMap<String, Object> usernameModify(String username, Long id) {
        List<String> findUsername = memberQueryRepository.findExistUsername(id).getResults();

        HashMap<String, Object> map = new HashMap<>();
        map.put("result", findUsername.contains(username));
        return map;
    }

    //닉네임 수정 중복 검사
    public HashMap<String, Object> nicknameModify(String nickname, Long id) {
        List<String> findNickname = memberQueryRepository.findExistNickname(id).getResults();

        HashMap<String, Object> map = new HashMap<>();
        map.put("result", findNickname.contains(nickname));
        return map;
    }

    /*회원 목록*/
    public List<MemberDto> getMemberList(int page, int perPage, String searchType, String keyword){
        List<MemberEntity> memberEntityList = memberQueryRepository.getMemberList(page,perPage,searchType,keyword);
        List<MemberDto> memberDtoList = new ArrayList<>();

        for(MemberEntity memberEntity : memberEntityList){
            memberDtoList.add(memberEntity.toDto());
        }
        return memberDtoList;
    }

    /*조회된 전체 데이터 수*/
    public int getTotalSize(String searchType, String keyword){
        return Math.toIntExact(memberQueryRepository.getTotalCount(searchType,keyword));
    }


}
