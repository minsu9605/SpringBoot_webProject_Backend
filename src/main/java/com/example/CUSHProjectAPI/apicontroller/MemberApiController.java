package com.example.CUSHProjectAPI.apicontroller;

import com.example.CUSHProjectAPI.dto.MemberDto;
import com.example.CUSHProjectAPI.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public class MemberApiController {

    private final MemberService memberService;

    @GetMapping("/api/member/info")
    public MemberDto getMemberByUsername(@RequestParam String username) {
        return memberService.getMemberByUsername(username);
    }
}
