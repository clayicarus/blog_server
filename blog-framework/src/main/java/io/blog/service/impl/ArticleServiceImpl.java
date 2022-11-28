package io.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.blog.constant.ArticleTableConstant;
import io.blog.ResponseResult;
import io.blog.entity.Article;
import io.blog.entity.Category;
import io.blog.service.CategoryService;
import io.blog.vo.ArticleDetailVo;
import io.blog.vo.ArticleListVo;
import io.blog.vo.HotArticleVo;
import io.blog.mapper.ArticleMapper;
import io.blog.service.ArticleService;
import io.blog.utils.BeanCopyUtils;
import io.blog.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Override
    public ResponseResult hotArticleList() {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<Article>();
        // not need to care about logic delete, coz setting in application.yml
        // descend less than 10 articles
        // must formal articles, not need draft
        queryWrapper.eq(Article::getStatus, ArticleTableConstant.ARTICLE_STATUS_PUBLISH); // use Article method to select
        // order by view count
        queryWrapper.orderByDesc(Article::getViewCount);
        // search for 10 article
        Page<Article> page = new Page(1, 10);

        // set queryWrapper
        page(page, queryWrapper);

        List<Article> articles = page.getRecords();

        return ResponseResult.okResult(BeanCopyUtils.copyBeanList(articles, HotArticleVo.class));
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        // query in article by category_id
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        // query when categoryId not null and is bigger than 0.
        wrapper.eq(categoryId != null && categoryId > 0, Article::getCategoryId, categoryId);
        wrapper.eq(Article::getStatus, ArticleTableConstant.ARTICLE_STATUS_PUBLISH);
        wrapper.orderByDesc(Article::getIsTop);
        // query by page
        Page<Article> page = new Page<>(pageNum, pageSize);
        page(page, wrapper);
        // vo
        List<Article> articles = page.getRecords();
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(articles, ArticleListVo.class);
        // set category name
        for(int i = 0; i < articleListVos.size(); ++i) {
            // not need to use hash, but use service.getById
            articleListVos.get(i).setCategoryName(categoryService.getById(articles.get(i).getCategoryId()).getName());
        }
        PageVo pageVo = new PageVo(articleListVos, page.getTotal());

        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticleDetail(Long id) {
        Article article = getById(id);
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        Category category = categoryService.getById(article.getCategoryId());
        if(category != null)
            articleDetailVo.setCategoryName(category.getName());

        return ResponseResult.okResult(articleDetailVo);
    }

    @Autowired
    private CategoryService categoryService;
}
