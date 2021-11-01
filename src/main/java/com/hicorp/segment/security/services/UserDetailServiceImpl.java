package com.hicorp.segment.security.services;

import com.hicorp.segment.mapper.MenuMapper;
import com.hicorp.segment.mapper.UserMapper;
import com.hicorp.segment.pojo.Menu;
import com.hicorp.segment.pojo.User;
import com.hicorp.segment.pojo.UserForSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: wqs
 * @Date: Created in 14:18 2021/5/5
 * @Description: 实现 UserDetailsService 接口，重写验证机制。
 * @Modified By:
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        User user = userMapper.findByUserName(s);

        if (user == null) throw new UsernameNotFoundException("admin: " + s + "do not exist!");

        Assert.isTrue(user.getUserName() != null && !"".equals(user.getUserName()) && user.getPassword() != null, "Cannot pass null or empty values to constructor");


        List<Menu> menuList;
//        List<Permission> permissions;
        if (user.getId() == 1) {
            menuList = menuMapper.selectAll();
//            permissions = permissionMapper.selectAll();
        } else {
            menuList = menuMapper.selectByUserId(user.getId());
//            permissions = permissionMapper.findPermissionByUserId(user.getId());
        }
//        menuMapper.select
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        if (CollectionUtils.isEmpty(grantedAuthorities)) {
//            permissions.forEach(p -> {
//                if (p != null && p.getSummary() != null) {
//                    // 将权限信息添加到 GrantedAuthority 对象中，在后面权限验证时会使用 GrantedAuthority 对象。
//                    // 如无特殊需求，请选择设置了 unique 属性的列！！！
//                    GrantedAuthority grantedAuthority = new GrantedAuthorityImpl(p.getApiPath(), p.getMethod());
//                    grantedAuthorities.add(grantedAuthority);
//                }
//            });
        }
        List<Menu> menus = new Menu().generateTree(menuList);
//        UserInfo userInfo = userInfoMapper.findByStaffNumber(s);


        return new UserForSecurity(user.getUserName(), user.getPassword(), grantedAuthorities, menus);
    }
}
