package io.blog.service.impl;

import io.blog.ResponseResult;
import io.blog.constant.RedisConstant;
import io.blog.entity.LoginUser;
import io.blog.entity.User;
import io.blog.service.LoginService;
import io.blog.utils.BeanCopyUtils;
import io.blog.utils.JwtUtil;
import io.blog.utils.RedisCache;
import io.blog.vo.LoginUserVo;
import io.blog.vo.UserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    @Override
    public ResponseResult login(User user)
    {
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        // pass token to authenticate
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        // is authenticated?
        if(authenticate == null) {
            throw new RuntimeException("username or password not valid");
        }
        // transmit userId to token(jwt)
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();  // any_cast
        String userId = loginUser.getUser().toString();
        String jwt = JwtUtil.createJWT(userId);
        // restore userinfo in redis
        redisCache.setCacheObject(RedisConstant.GET_USER_BY_USERID + userId, loginUser.getUser()); // loginUser is UserDetails
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser, UserInfoVo.class);
        LoginUserVo vo = new LoginUserVo(jwt, userInfoVo);

        return ResponseResult.okResult(vo);
    }
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;
}
