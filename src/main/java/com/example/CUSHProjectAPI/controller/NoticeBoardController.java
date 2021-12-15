package com.example.CUSHProjectAPI.controller;

import com.example.CUSHProjectAPI.dto.NoticeBoardDto;
import com.example.CUSHProjectAPI.service.NoticeBoardService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    @GetMapping("/api/notice/list/table")
    @ResponseBody
    public HashMap<String, Object> getNoticeList( @RequestParam(required = false) int page,
                                                  @RequestParam(required = false) int perPage,
                                                  @RequestParam(required = false) String searchType,
                                                  @RequestParam(required = false, defaultValue = "") String keyword){
        HashMap<String, Object> objectMap = new HashMap<>();
        HashMap<String, Object> dataMap = new HashMap<>();
        HashMap<String, Object> paginationMap = new HashMap<>();


        int total = noticeBoardService.getTotalSize(searchType,keyword);
        List<NoticeBoardDto> noticeBoardDtoList = noticeBoardService.getNoticeBoardList(page, perPage, searchType, keyword);

        objectMap.put("result", true);
        objectMap.put("data", dataMap);
        dataMap.put("contents", noticeBoardDtoList);
        dataMap.put("pagination", paginationMap);
        paginationMap.put("page", page);
        paginationMap.put("totalCount", total);
        return objectMap;
    }

    //글쓰기
    @GetMapping("/notice/write")
    public String noticeBoardWrite(Model model) {
        NoticeBoardDto boardForm = new NoticeBoardDto();

        model.addAttribute("boardForm",boardForm);
        return "notice/noticeform";
    }

    @PostMapping("/notice/write")
    public String noticeBoardWrite(NoticeBoardDto noticeBoardDto, Authentication authentication, HttpServletRequest request){
        noticeBoardService.noticeBoardWrite(noticeBoardDto, authentication.getName(), request);
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
    @DeleteMapping("/api/notice/delete")
    public void noticeBoardDelete(@RequestParam(required = false)Long id) {
        noticeBoardService.noticeBoardDelete(id);
    }

}
