package com.hicorp.segment.pojo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;

/**
 * @Author: wqs
 * @Date: Created in 11:52 2021/5/25
 * @Description:
 * @ChineseDescription:
 * @Modified_By:
 */
@Getter
@Setter
public class UserForSecurity extends User {

    private List<Menu> menu;
    private UserInfo userInfo;


    public UserForSecurity(String username, String password, Collection<? extends GrantedAuthority> authorities, List<Menu> menuList, UserInfo userInfo) {
        super(username, password, authorities);
        this.menu = menuList;
        this.userInfo = userInfo;
    }
}
