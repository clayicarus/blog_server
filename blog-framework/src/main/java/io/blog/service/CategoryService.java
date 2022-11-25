package io.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.blog.ResponseResult;
import io.blog.entity.Category;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2022-11-25 11:40:14
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();
}

