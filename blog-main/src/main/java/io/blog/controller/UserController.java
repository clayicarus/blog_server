package io.blog.controller;

import io.blog.ResponseResult;
import io.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @GetMapping("/userInfo")
    public ResponseResult userInfo()
    {
        return userService.userInfo();
    }
    @Autowired
    private UserService userService;
}
