package io.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.blog.ResponseResult;
import io.blog.constant.ArticleTableConstant;
import io.blog.entity.Article;
import io.blog.entity.Category;
import io.blog.mapper.CategoryMapper;
import io.blog.service.ArticleService;
import io.blog.service.CategoryService;
import io.blog.utils.BeanCopyUtils;
import io.blog.vo.CategoryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2022-11-25 11:40:14
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Override
    public ResponseResult getCategoryList() {
        // query article, which status publish
        LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper<>();
        articleWrapper.eq(Article::getStatus, ArticleTableConstant.ARTICLE_STATUS_PUBLISH);
        List<Article> articleList = articleService.list(articleWrapper);    // list by a wrapper
        // get unique category_id which exist in article
        Set<Long> exist_id = articleList.stream().map(Article::getCategoryId).collect(Collectors.toSet());
        // query category
        LambdaQueryWrapper<Category> categoryWrapper = new LambdaQueryWrapper<>();
        categoryWrapper.eq(Category::getStatus, ArticleTableConstant.CATEGORY_STATUS_ENABLE);
        categoryWrapper.in(Category::getId, exist_id);
        List<Category> categories = list(categoryWrapper);
        // vo
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);

        return ResponseResult.okResult(categoryVos);
    }

    @Autowired  // 注入？
    private ArticleService articleService;
}

