package io.blog.utils;

import io.blog.entity.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
    public static LoginUser getLoginUser()
    {
        return (LoginUser)getAuthentication().getPrincipal();
    }
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
    public static Long getUserId() {
        return getLoginUser().getUser().getId();
    }
}
