package com.example.CUSHProject.controller;

import com.example.CUSHProject.dto.NoticeBoardDto;
import com.example.CUSHProject.service.NoticeBoardService;
import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

@Controller
@AllArgsConstructor
public class NoticeBoardController {

    private final NoticeBoardService noticeBoardService;
    /*공지사항*/
    @GetMapping("/notice/list")
    public String noticeList() {
        return "notice/noticelist";
    }

    /*공지사항 api*/
    @GetMapping("/notice/list/table")
    @ResponseBody
    public HashMap<String, Object> getNoticeList(){
        HashMap<String, Object> map = new HashMap<>();

        List<NoticeBoardDto> noticeBoardDtoList = noticeBoardService.getNoticeBoardList();
        map.put("list", noticeBoardDtoList);
        return map;
    }

    //글쓰기
    @GetMapping("/notice/write")
    public String noticeBoardWrite(Model model) {
        NoticeBoardDto boardForm = new NoticeBoardDto();

        model.addAttribute("boardForm",boardForm);
        return "notice/noticeform";
    }

    @PostMapping("/notice/write")
    public String noticeBoardWrite(NoticeBoardDto noticeBoardDto, Authentication authentication){
        noticeBoardService.noticeBoardWrite(noticeBoardDto, authentication.getName());
        return "redirect:/notice/list";
    }

    @GetMapping("/notice/content")
    public String noticeBoardContent(Model model, @RequestParam(required = false) Long id){
        noticeBoardService.noticeBoardHitUpdate(id);
        NoticeBoardDto boardForm = noticeBoardService.noticeBoardContent(id);

        model.addAttribute("boardForm",boardForm);
        return "notice/noticecontent";
    }

    @GetMapping("/notice/modify")
    public String noticeBoardModify(Model model, @RequestParam(required = false) Long id){
        NoticeBoardDto boardForm = noticeBoardService.noticeBoardContent(id);

        model.addAttribute("boardForm",boardForm);

        return "notice/noticemodify";
    }

    @PostMapping("/notice/modify")
    public String noticeBoardModify(NoticeBoardDto noticeBoardDto, Authentication authentication){
        noticeBoardService.boardModifySave(noticeBoardDto, authentication.getName());
        return "redirect:/notice/list";
    }

    @ResponseBody
    @DeleteMapping("/notice/delete")
    public void noticeBoardDelete(@RequestParam(required = false)Long id) {
        noticeBoardService.noticeBoardDelete(id);
    }

}
