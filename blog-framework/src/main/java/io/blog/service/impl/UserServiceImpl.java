package io.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.blog.ResponseResult;
import io.blog.entity.User;
import io.blog.mapper.UserMapper;
import io.blog.service.UserService;
import io.blog.utils.BeanCopyUtils;
import io.blog.utils.SecurityUtils;
import io.blog.vo.user_vo.UserInfoVo;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public ResponseResult userInfo() {
        //获取当前用户id
        Long userId = SecurityUtils.getUserId();
        //根据用户id查询用户信息
        User user = getById(userId);
        //封装成UserInfoVo
        UserInfoVo vo = BeanCopyUtils.copyBean(user,UserInfoVo.class);
        return ResponseResult.okResult(vo);
    }
}
