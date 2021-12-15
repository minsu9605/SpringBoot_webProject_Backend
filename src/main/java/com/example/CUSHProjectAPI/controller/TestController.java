package com.example.CUSHProjectAPI.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @GetMapping("/aaa")
    public String aaa() {
        return "test/aaa";
    }


    @GetMapping("/bbb")
    public String bbb() {
        return "test/bbb";
    }

}


