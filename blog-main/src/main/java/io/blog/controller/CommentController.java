package io.blog.controller;

import io.blog.ResponseResult;
import io.blog.entity.Comment;
import io.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @GetMapping("/commentList")
    public ResponseResult commentList(@RequestParam(name = "articleId") Long articleId,
                                      @RequestParam(name = "pageNum") Integer pageNum,
                                      @RequestParam(name = "pageSize") Integer pageSize)
    {
        return commentService.commentList(articleId, pageNum, pageSize);
    }
    @PostMapping
    public ResponseResult addComment(@RequestBody Comment comment)
    {
        return commentService.addComment(comment);
    }
    @Autowired
    private CommentService commentService;
}
