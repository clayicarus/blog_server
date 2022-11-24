package io.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.blog.domain.ResponseResult;
import io.blog.domain.entity.Article;

public interface ArticleService extends IService<Article> {
    ResponseResult hotArticleList();    // interface declare here, implement in Impl
}
