package com.example.CUSHProjectAPI.apicontroller;

import com.example.CUSHProjectAPI.dto.*;
import com.example.CUSHProjectAPI.service.BoardService;
import com.example.CUSHProjectAPI.service.CategoryService;
import com.example.CUSHProjectAPI.service.NoticeBoardService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    @GetMapping("/api/notice/hitUpdate")
    public void noticeHitUpdate(Long id) {
        noticeBoardService.noticeBoardHitUpdate(id);
    }

    @GetMapping("/api/notice/getContent")
    public NoticeBoardDto getNoticeBoardContent(Long id) {
        return noticeBoardService.noticeBoardContent(id);
    }

    @PostMapping("/api/notice/write")
    public HashMap<String, Object> noticeBoardWrite(NoticeBoardDto noticeBoardDto, HttpServletRequest request){
        HashMap<String, Object> map = new HashMap<>();
        noticeBoardService.noticeBoardWrite(noticeBoardDto,noticeBoardDto.getWriter(),request);
        map.put("result","success");
        return map;
    }

    @PostMapping("/api/notice/modify")
    public HashMap<String, Object> noticeBoardModify(NoticeBoardDto noticeBoardDto){
        HashMap<String, Object> map = new HashMap<>();
        noticeBoardService.boardModifySave(noticeBoardDto, noticeBoardDto.getWriter());
        map.put("noticeId",noticeBoardDto.getId());
        return map;
    }

    @DeleteMapping("/api/notice/delete")
    public HashMap<String, Object> boardDelete(@RequestParam(required = false) Long id) {
        HashMap<String, Object> map = new HashMap<>();
        noticeBoardService.noticeBoardDelete(id);
        map.put("result", "success");
        return map;
    }
}
