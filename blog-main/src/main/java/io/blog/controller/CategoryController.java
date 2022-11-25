package io.blog.controller;

import io.blog.ResponseResult;
import io.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @GetMapping("/getCategoryList")
    public ResponseResult getCategoryList()
    {
        return categoryService.getCategoryList();
    }

    @Autowired
    private CategoryService categoryService;
}
