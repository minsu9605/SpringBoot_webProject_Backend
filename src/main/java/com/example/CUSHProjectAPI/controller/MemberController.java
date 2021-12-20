package com.example.CUSHProjectAPI.controller;

import com.example.CUSHProjectAPI.dto.MemberDto;
import com.example.CUSHProjectAPI.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Controller
@AllArgsConstructor
public class MemberController {

    private final MemberService memberService;

    //로그아웃
    @GetMapping("/account/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    //로그인 페이지
    @GetMapping("/account/login")
    public String loginPage(HttpServletRequest request) {
        //이전 페이지의 정보를 담음
        String url = request.getHeader("Referer");
        if(!url.contains("/account/login")) {
            request.getSession().setAttribute("prevPage",request.getHeader("Referer"));
        }
        return "account/login";
    }

    //로그인 처리
    @PostMapping("/account/login")
    public String loginError(HttpServletRequest request, Model model) {
        String loginFailMsg = (String) request.getAttribute("loginFailMsg");
        model.addAttribute("loginFailMsg",loginFailMsg);
        return "account/login";
    }

    //회원가입 페이지
    @GetMapping("/account/register")
    public String registerPage(Model model) {
        model.addAttribute("register", new MemberDto());
        return "account/register";
    }

   /* //회원가입 처리
    @PostMapping("/account/register")
    public String register(MemberDto memberDto) {
        memberService.singUp(memberDto);
        return "redirect:/";
    }

    //내정보 수정 페이지
    @GetMapping("/account/myinfo")
    public String myInfo(Authentication authentication, Model model) {
        MemberDto memberDto = memberService.getMemberByUsername(authentication.getName());
        model.addAttribute("member",memberDto);

        return "account/myinfo";
    }*/
    //내정보 수정 페이지 처리
    @PostMapping("/account/myinfo")
    public String memberUpdate(MemberDto memberDto) {
        memberService.memberUpdate(memberDto);
        return "redirect:/";
    }

    //패스워드 수정페이지
    @GetMapping("/account/password")
    public String modifyPassword() {
        return "account/password";
    }

    /*//패스워드 확인
    @ResponseBody
    @GetMapping("/api/pwCheck")
    public HashMap<String, Object> pwCheck(@RequestParam(required = false) String original_Pw, Authentication authentication) {
        return memberService.pwCheck(authentication,original_Pw);
    }*/

    /*//패스워드 업데이트
    @PostMapping("/account/password")
    public String passwordInfo(Authentication authentication,MemberDto memberDto) {
        memberDto.setUsername(authentication.getName());
        memberService.passwordUpdate(memberDto);
        return "redirect:/account/logout";
    }

    //회원탈퇴
    @GetMapping("/account/withdrawal")
    public String withdrawalMember(Authentication authentication, Model model) {
        MemberDto memberDto = memberService.getMemberByUsername(authentication.getName());
        model.addAttribute("member", memberDto);
        return "account/withdrawal";
    }*/

    //회원 탈퇴 실행
    @PostMapping("/account/withdrawal")
    public String withdrawalMember(MemberDto memberDto, HttpSession session) {
        memberService.deleteUser(memberDto.getId());
        session.invalidate();
        return "redirect:/";
    }
}