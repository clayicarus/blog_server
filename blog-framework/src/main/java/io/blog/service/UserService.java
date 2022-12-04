package io.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.blog.ResponseResult;
import io.blog.entity.User;

public interface UserService extends IService<User> {

    ResponseResult userInfo();
}
