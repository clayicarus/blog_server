package io.blog.controller;

import io.blog.ResponseResult;
import io.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    @Autowired
    private CommentService commentService;
}
