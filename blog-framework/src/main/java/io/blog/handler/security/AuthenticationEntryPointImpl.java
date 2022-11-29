package io.blog.handler.security;

import com.alibaba.fastjson.JSON;
import io.blog.ResponseResult;
import io.blog.enums.AppHttpCodeEnum;
import io.blog.utils.WebUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        authException.printStackTrace();
        // InsufficientAuthenticationException is no token, need login
        // BadCredentialsException is username or password error
        ResponseResult res = null;
        if(authException instanceof BadCredentialsException) {
            // directly use authException.message
            res = ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR);
        } else if(authException instanceof InsufficientAuthenticationException) {
            res = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        } else {
            res = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(),"认证或授权失败");
        }
        WebUtils.renderString(response, JSON.toJSONString(res));
        // here not require to return
    }
}
