package com.example.CUSHProjectAPI.controller;


import com.example.CUSHProjectAPI.dto.OAuthToken;
import com.example.CUSHProjectAPI.repository.MemberRepository;
import com.example.CUSHProjectAPI.service.KakaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class KakaoController {
    @Autowired
    private KakaoService kakaoService;

    @GetMapping(value="/kakao/oauth")
    public String kakaoConnect() {

        StringBuffer url = new StringBuffer();
        url.append("https://kauth.kakao.com/oauth/authorize?");
        url.append("client_id=f795106be2877118844d807a1e9b8cc9");
        url.append("&redirect_uri=http://localhost:8080/kakao/callback");
        url.append("&response_type=code");

        return "redirect:" + url;
    }

        @GetMapping(value = "/kakao/callback")
        public String kakaoOauthRedirect(@RequestParam String code,OAuthToken oauthToken, MemberRepository memberRepository) throws Exception {
            OAuthToken oAuthToken = kakaoService.getAccessToken(code);
//            return kakaoService.getKakaoProfile(oAuthToken,memberRepository);
            return "index";
        }
}