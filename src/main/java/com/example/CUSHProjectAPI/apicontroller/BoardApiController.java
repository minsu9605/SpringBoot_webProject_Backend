package com.example.CUSHProjectAPI.apicontroller;

import com.example.CUSHProjectAPI.dto.BoardCategoryDto;
import com.example.CUSHProjectAPI.dto.BoardDto;
import com.example.CUSHProjectAPI.dto.BoardListDto;
import com.example.CUSHProjectAPI.service.BoardService;
import com.example.CUSHProjectAPI.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class BoardApiController {

    private final BoardService boardService;
    private final CategoryService categoryService;

    /*일반 게시판*/
    @GetMapping("/api/board/list/getCategoryList")
    public List<BoardCategoryDto> getCategoryList() {
        return categoryService.getCategoryList();
    }

    /*일반 게시판 api*/
    @GetMapping("/api/board/list/table")
    public HashMap<String, Object> getBoardList(BoardListDto boardListDto) {

        HashMap<String, Object> objectMap = new HashMap<>();
        HashMap<String, Object> dataMap = new HashMap<>();
        HashMap<String, Object> paginationMap = new HashMap<>();

        int total = boardService.getTotalSize(boardListDto.getCategoryId(),boardListDto.getSearchType(),boardListDto.getKeyword());
        List<BoardDto> boardDtoList = boardService.getBoardList(boardListDto);

        objectMap.put("result", true);
        objectMap.put("data", dataMap);
        dataMap.put("contents", boardDtoList);
        dataMap.put("pagination", paginationMap);
        paginationMap.put("page", boardListDto.getPage());
        paginationMap.put("totalCount", total);
        return objectMap;
    }

    @PostMapping("/api/board/write")
    public HashMap<String, Object> boardWrite(BoardDto boardDto, HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<>();
        BoardDto savedDto = boardService.boardWrite(boardDto, boardDto.getWriter(), request);
        map.put("categoryId",savedDto.getCategoryId());
        return map;
    }

    @GetMapping("/api/board/getMyOldBoardCnt")
    public int getMyOldBoardAlertCnt(String username) {
        return boardService.getMyOldBoardAlertListCnt(username);
    }

    @GetMapping("/api/board/getMyOldBoardList")
    public List<BoardDto> oldBoardAlertList(String username, int startIndex, int searchStep) {
        return boardService.getMyOldBoardAlertList(username, startIndex, searchStep);
    }

    @GetMapping("/api/board/hitUpdate")
    public void boardHitUpdate(Long id) {
        boardService.boardHitUpdate(id);
    }

    @GetMapping("/api/board/getBoardContent")
    public BoardDto getBoardContent(Long id) {
        return boardService.boardContent(id);
    }

    @GetMapping("/api/board/setSoldOut")
    public void setSoldOut(Long id) {
        boardService.setSoldOut(id);
    }
}
