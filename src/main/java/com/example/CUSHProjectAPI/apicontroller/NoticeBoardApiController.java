package com.example.CUSHProjectAPI.apicontroller;

import com.example.CUSHProjectAPI.dto.*;
import com.example.CUSHProjectAPI.service.BoardService;
import com.example.CUSHProjectAPI.service.CategoryService;
import com.example.CUSHProjectAPI.service.NoticeBoardService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class NoticeBoardApiController {

    private final NoticeBoardService noticeBoardService;
    /*공지사항 api*/
    @GetMapping("/api/notice/list/table")
    @ResponseBody
    public HashMap<String, Object> getNoticeList(NoticeBoardListDto noticeboardListDto){

        log.info("page: "+noticeboardListDto.getPage());
        log.info("perPage: "+ noticeboardListDto.getPerPage());
        log.info("searchType: "+ noticeboardListDto.getSearchType());
        log.info("keyword: "+ noticeboardListDto.getKeyword());

        HashMap<String, Object> objectMap = new HashMap<>();
        HashMap<String, Object> dataMap = new HashMap<>();
        HashMap<String, Object> paginationMap = new HashMap<>();


        int total = noticeBoardService.getTotalSize(noticeboardListDto.getSearchType(),noticeboardListDto.getKeyword());
        List<NoticeBoardDto> noticeBoardDtoList = noticeBoardService.getNoticeBoardList(noticeboardListDto);

        objectMap.put("result", true);
        objectMap.put("data", dataMap);
        dataMap.put("contents", noticeBoardDtoList);
        dataMap.put("pagination", paginationMap);
        paginationMap.put("page", noticeboardListDto.getPage());
        paginationMap.put("totalCount", total);
        return objectMap;
    }

}
