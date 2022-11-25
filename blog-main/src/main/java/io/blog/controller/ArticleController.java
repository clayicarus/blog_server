package io.blog.controller;

import io.blog.ResponseResult;
import io.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// 返回json
@RestController
@RequestMapping("/article")
public class ArticleController {

    // 处理get请求
    @GetMapping("/list")
    public ResponseResult test()
    {
        return ResponseResult.okResult(articleService.list());
    }
    @GetMapping("/hotArticleList")
    public ResponseResult hotArticleList()
    {
        ResponseResult result = articleService.hotArticleList();
        return result;
    }
    @Autowired
    private ArticleService articleService;
}
