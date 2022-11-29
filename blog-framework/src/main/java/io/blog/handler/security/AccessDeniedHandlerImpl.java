package io.blog.handler.security;

import com.alibaba.fastjson.JSON;
import io.blog.ResponseResult;
import io.blog.enums.AppHttpCodeEnum;
import io.blog.utils.WebUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        accessDeniedException.printStackTrace();
        ResponseResult res = ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR);
        WebUtils.renderString(response, JSON.toJSONString(res));
        // here not require to return
    }
}
