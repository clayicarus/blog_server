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
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN.getCode(), "Not valid token");
            WebUtils.renderString(response, JSON.toJSONString(result)); // rewrite response
            return;
        }
        String userId = claims.getSubject();
        // here is not the same with example
        // now use LoginUser
        LoginUser loginUser = redisCache.getCacheObject(RedisConstant.GET_USER_BY_USERID + userId);
        if(loginUser == null) {
            // login out of date / logout
            // re-login
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN.getCode(), "登陆已过期");
            WebUtils.renderString(response, JSON.toJSONString(result)); // rewrite response
            return;
        }
        // principal is LoginUser coz use loginUser to init a token (omoshiroi desu)
        UsernamePasswordAuthenticationToken authenticationToken
                =  new UsernamePasswordAuthenticationToken(loginUser, null, null);
        // use context for logout
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
    @Autowired
    private RedisCache redisCache;
}
