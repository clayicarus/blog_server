package io.blog.controller;

import io.blog.ResponseResult;
import io.blog.entity.User;
import io.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    @PutMapping("/userInfo")
    public ResponseResult updateUserInfo(User user)
    {
        return userService.updateUserInfo(user);
    }
    @Autowired
    private UserService userService;
}
