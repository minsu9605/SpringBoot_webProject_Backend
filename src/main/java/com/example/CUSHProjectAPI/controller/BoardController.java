package com.example.CUSHProjectAPI.controller;

import com.example.CUSHProjectAPI.dto.BoardCategoryDto;
import com.example.CUSHProjectAPI.dto.BoardDto;
import com.example.CUSHProjectAPI.service.BoardService;
import com.example.CUSHProjectAPI.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@Controller
@AllArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final CategoryService categoryService;

    @GetMapping("/board/map")
    public String showMap() {
        return "board/map";
    }

    @GetMapping("/board/map_content")
    public String showMapContent() {
        return "board/map_content";
    }

    @ResponseBody
    @PostMapping("/api/uploadSummernoteImageFile")
    public HashMap<String, Object> uploadSummernoteImageFile(@RequestParam("file") MultipartFile multipartFile) {
        return boardService.boardImageUpload(multipartFile);
    }

    //내가 쓴 게시물(내정보)
    @GetMapping("/board/myBoard")
    public String myBoard() {
        return "account/myboard";
    }

    //내가 쓴 오래된 게시물
    @GetMapping("/board/myOldBoard")
    public String myOldBoard() {
        return "account/myOldBoard";
    }

   /* *//*내가 쓴 오래된 게시물 api*//*
    @GetMapping("/api/board/myOldBoard/table")
    @ResponseBody
    public HashMap<String, Object> getMyOldBoardList(@RequestParam(required = false) int page,
                                                     @RequestParam(required = false) int perPage,
                                                     @RequestParam(required = false) String searchType,
                                                     @RequestParam(required = false, defaultValue = "") String keyword, Authentication authentication) {
        HashMap<String, Object> objectMap = new HashMap<>();
        HashMap<String, Object> dataMap = new HashMap<>();
        HashMap<String, Object> paginationMap = new HashMap<>();

        int total = boardService.getMyOldBoardTotalSize(authentication.getName(), searchType, keyword);
        List<BoardDto> boardEntityList = boardService.getMyOldBoardList(authentication.getName(), page, perPage, searchType, keyword);

        objectMap.put("result", true);
        objectMap.put("data", dataMap);
        dataMap.put("contents", boardEntityList);
        dataMap.put("pagination", paginationMap);
        paginationMap.put("page", page);
        paginationMap.put("totalCount", total);
        return objectMap;
    }*/

    @GetMapping("/api/board/setAlertReading")
    @ResponseBody
    public HashMap<String, Object> setAlertReading(@RequestParam(required = false) Long id) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("result",boardService.setAlertReading(id));
        return map;
    }

    /*내가 쓴 게시물 api*//*
    @GetMapping("/api/board/myBoard/table")
    @ResponseBody
    public HashMap<String, Object> getMyBoardList(@RequestParam(required = false) int page,
                                                 @RequestParam(required = false) int perPage,
                                                 @RequestParam(required = false) String searchType,
                                                 @RequestParam(required = false, defaultValue = "") String keyword, Authentication authentication) {
        HashMap<String, Object> objectMap = new HashMap<>();
        HashMap<String, Object> dataMap = new HashMap<>();
        HashMap<String, Object> paginationMap = new HashMap<>();

        int total = boardService.getMyBoardTotalSize(authentication.getName(), searchType, keyword);
        List<BoardDto> boardEntityList = boardService.getMyBoardList(authentication.getName(), page, perPage, searchType, keyword);

        objectMap.put("result", true);
        objectMap.put("data", dataMap);
        dataMap.put("contents", boardEntityList);
        dataMap.put("pagination", paginationMap);
        paginationMap.put("page", page);
        paginationMap.put("totalCount", total);
        return objectMap;
    }*/

    @ResponseBody
    @GetMapping("/api/admin/adminBoardChart")
    public HashMap<String, Object> adminBoardCnt(@RequestParam(required = false) String monthOption,
                                                 @RequestParam(required = false) String yearOption) {
        return boardService.getBoardCount(yearOption, monthOption);
    }

}
