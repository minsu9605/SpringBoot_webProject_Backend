package com.example.CUSHProjectAPI.controller;

import com.example.CUSHProjectAPI.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;

@Controller
@RequiredArgsConstructor
public class OverlapController {

    private final MemberService memberService;

    //회원가입 이메일 중복 체크
    @ResponseBody
    @GetMapping("/api/overlap/usernameRegister")
    public HashMap<String, Object> usernameOverlap(@RequestParam(value = "username", required = false) String username) {
        return memberService.usernameOverlap(username);
    }

    //회원가입 닉네임 중복 체크
    @ResponseBody
    @GetMapping("/api/overlap/nicknameRegister")
    public HashMap<String, Object> nicknameOverlap(@RequestParam(value = "nickname", required = false) String nickname) {
        return memberService.nicknameOverlap(nickname);
    }

    //아이디 수정 중복 체크
    @ResponseBody
    @PostMapping("/api/usernameModify")
    public HashMap<String, Object> usernameModify(@RequestParam(required = false) Long id, String username) {
        return memberService.usernameModify(username, id);
    }

    //닉네임 수정 중복 체크
    @ResponseBody
    @PostMapping("/api/nicknameModify")
    public HashMap<String, Object> nicknameModify(@RequestParam(required = false) Long id, String nickname) {
        return memberService.nicknameModify(nickname, id);
    }
}
