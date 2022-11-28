package io.blog.filter;

import com.alibaba.fastjson.JSON;
import io.blog.ResponseResult;
import io.blog.constant.RedisConstant;
import io.blog.entity.LoginUser;
import io.blog.entity.User;
import io.blog.enums.AppHttpCodeEnum;
import io.blog.utils.JwtUtil;
import io.blog.utils.RedisCache;
import io.blog.utils.WebUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("token");
        if(token == null) {
            filterChain.doFilter(request, response);    // not need to retrieve token
            return;
        }
        Claims claims;
        try {
            claims = JwtUtil.parseJWT(token);
        } catch (Exception e) {
            e.printStackTrace();
            // token not valid
            // re-login
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(result)); // rewrite response
            return;
        }
        String userId = claims.getSubject();
        User user = redisCache.getCacheObject(RedisConstant.GET_USER_BY_USERID + userId);
        if(user == null) {
            // login out of date
            // re-login
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_OUT_OF_DATE);
            WebUtils.renderString(response, JSON.toJSONString(result)); // rewrite response
            return;
        }
        UsernamePasswordAuthenticationToken authenticationToken
                =  new UsernamePasswordAuthenticationToken(user, null, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
    @Autowired
    private RedisCache redisCache;
}
