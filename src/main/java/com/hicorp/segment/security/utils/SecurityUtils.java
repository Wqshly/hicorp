package com.hicorp.segment.security.utils;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * @Author: wqs
 * @Date: Created in 17:09 2021/5/29
 * @Description:
 * @ChineseDescription: 该类用于读取当前登录的用户的相关信息
 * @Modified_By:
 */
@Component
public class SecurityUtils {
    public UserDetails getUserInfo() {
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication() .getPrincipal();
    }

    public String getUserName() {
        // 执行当前操作的用户
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            assert authentication != null;
            return authentication.getName();
        } else {
            return "";
        }
    }
}
