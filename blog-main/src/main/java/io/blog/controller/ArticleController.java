package io.blog.controller;

import io.blog.ResponseResult;
import io.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/articleList")
    public ResponseResult articleList(@RequestParam(name = "page_num") Integer pageNum,
                                      @RequestParam(name = "page_size") Integer pageSize,
                                      @RequestParam(name = "category_id", required = false) Long categoryId)
    {
        ResponseResult result = articleService.articleList(pageNum, pageSize, categoryId);
        return result;
    }
    @GetMapping("/{id}")
    public ResponseResult getArticleDetail(@PathVariable("id") Long id)
    {
        return articleService.getArticleDetail(id);
    }
    @Autowired
    private ArticleService articleService;
}
