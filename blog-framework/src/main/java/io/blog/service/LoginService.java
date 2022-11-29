package io.blog.service;

import io.blog.ResponseResult;
import io.blog.entity.User;

public interface LoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
