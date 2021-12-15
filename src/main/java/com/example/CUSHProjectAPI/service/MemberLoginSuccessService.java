package com.example.CUSHProjectAPI.service;

import com.example.CUSHProjectAPI.dto.MemberDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Service
public class MemberLoginSuccessService implements AuthenticationSuccessHandler {

    @Resource
    private MemberService memberService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        /*강제 인터셉트 당했을 경우의 데이터 get*/
        RequestCache requestCache = new HttpSessionRequestCache();
        SavedRequest savedRequest = requestCache.getRequest(request, response);

        /*로그인 버튼 눌러 접속했을 경우의 데이터 get*/
        String prevPage = (String) request.getSession().getAttribute("prevPage");

        //url 기본 값
        String url = "/";


        /*이전 페이지가 접근 제한 페이지일 경우*/
        if (savedRequest != null) {
            url = savedRequest.getRedirectUrl();

            /*로그인 버튼 눌러서 접근한 경우*/
        } else if (prevPage != null) {
            url = prevPage;
        }

        /*로그인 완료시 세션저장*/
        MemberDto memberDto = memberService.memberInfo(authentication.getName());

        HttpSession session = request.getSession(false);
        session.setAttribute("memberId", memberDto.getId());
        session.setAttribute("memberUsername", memberDto.getUsername());
        session.setAttribute("memberNickname", memberDto.getNickname());
        session.setAttribute("memberGender", memberDto.getGender());
        session.setAttribute("memberAge", memberDto.toEntity().getAge());
        session.setAttribute("memberRole", memberDto.getRole());

        response.sendRedirect(url);
    }
}
