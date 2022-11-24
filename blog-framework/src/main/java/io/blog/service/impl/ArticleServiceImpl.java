package io.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.util.BeanUtil;
import io.blog.constant.Constant;
import io.blog.domain.ResponseResult;
import io.blog.domain.entity.Article;
import io.blog.domain.vo.HotArticleVo;
import io.blog.mapper.ArticleMapper;
import io.blog.service.ArticleService;
import io.blog.utils.BeanCopyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Override
    public ResponseResult hotArticleList() {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<Article>();
        // not need to care about logic delete, coz setting in application.yml
        // descend less than 10 articles
        // must formal articles, not need draft
        queryWrapper.eq(Article::getStatus, Constant.ARTICLE_STATUS_PUBLIC); // use Article method to select
        // order by view count
        queryWrapper.orderByDesc(Article::getViewCount);
        // search for 10 article
        Page<Article> page = new Page(1, 10);

        // set queryWrapper
        page(page, queryWrapper);

        List<Article> articles = page.getRecords();

        // bean copy
        // by var name
        // List<HotArticleVo> vos = new ArrayList<HotArticleVo>();
        // for(Article i : articles) {
        //     vos.add(BeanCopyUtils.copyBean(i, HotArticleVo.class));
        // }
        ;

        return ResponseResult.okResult(BeanCopyUtils.copyBeanList(articles, HotArticleVo.class));
    }
}
