package io.blog.controller;

import io.blog.ResponseResult;
import io.blog.entity.User;
import io.blog.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user)
    {
        return loginService.login(user);
    }
    @Autowired
    private LoginService loginService;
}
