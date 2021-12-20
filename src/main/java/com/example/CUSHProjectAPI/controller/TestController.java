package com.example.CUSHProjectAPI.controller;

import com.example.CUSHProjectAPI.dto.TestDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {

    /*@GetMapping("/hello")
    public String hello(TestDto testDto) {
        String response =testDto.getId()+testDto.getWriter()+"안녕하세요 RestTemplate";
        return response;
    }*/

    @GetMapping("/hello")
    public String hello(@RequestParam String msg1, @RequestParam int msg2) {
        String response =msg1+msg2+"안녕하세요 RestTemplate";
        return response;
    }

    /*@GetMapping("/hello")
    public String hello(@RequestBody TestDto testDto ) {
        String response = testDto.msg1+"안녕하세요 RestTemplate";
        return response;
    }
*/
}


