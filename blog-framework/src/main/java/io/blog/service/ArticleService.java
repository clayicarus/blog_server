package io.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.blog.ResponseResult;
import io.blog.entity.Article;

public interface ArticleService extends IService<Article> {
    ResponseResult hotArticleList();    // interface declare here, implement in Impl

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetail(Long id);
}
