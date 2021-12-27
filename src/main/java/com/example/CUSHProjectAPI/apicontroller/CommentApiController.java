package com.example.CUSHProjectAPI.apicontroller;

import com.example.CUSHProjectAPI.dto.BoardCategoryDto;
import com.example.CUSHProjectAPI.dto.BoardDto;
import com.example.CUSHProjectAPI.dto.BoardListDto;
import com.example.CUSHProjectAPI.service.BoardService;
import com.example.CUSHProjectAPI.service.CategoryService;
import com.example.CUSHProjectAPI.service.CommentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class CommentApiController {

    private final CommentService commentService;

    /*일반 게시판 api*/
    @GetMapping("/api/comment/list")
    public HashMap<String, Object> getCommentList(Long bid) {
        return commentService.getCommentList(bid);
    }

    @PostMapping("/api/comment/post")
    public HashMap<String, Object> commentPost(Long bid, String comment, String username) throws Exception {
        int cDepth = 0;
        Long cGroup = 0L;

        return commentService.commentPost(bid, comment, cDepth, cGroup, username);
    }

    @GetMapping("/api/comment/reComment/list")
    public HashMap<String, Object> getReCommentList(Long cid){
        return commentService.getReCommentList(cid);
    }

    @PostMapping("/api/comment/reComment/post")
    public HashMap<String, Object> reCommentPost(Long bid,Long cid, String comment,  String username) throws Exception {
        int cDepth = 1;

        return commentService.commentPost(bid, comment, cDepth, cid, username);
    }

    @DeleteMapping("/api/comment/delete")
    public HashMap<String, Object> deleteComment(Long cid, Object roleSession) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("result",commentService.deleteComment(cid, roleSession));
        return map;
    }

    @GetMapping("/api/comment/modify")
    public HashMap<String, Object> modifyComment(Long cid, String comment) {
        return commentService.modifyComment(cid, comment);
    }
}
