package com.hicorp.segment.controller;

import com.github.pagehelper.PageInfo;
import com.hicorp.segment.pojo.PasswordDO;
import com.hicorp.segment.pojo.PasswordFindDO;
import com.hicorp.segment.pojo.User;
import com.hicorp.segment.pojo.UserInfo;
import com.hicorp.segment.security.utils.SecurityUtils;
import com.hicorp.segment.service.UserInfoService;
import com.hicorp.segment.service.UserService;
import com.hicorp.segment.swagger.ApiVersion;
import com.hicorp.segment.swagger.ApiVersionConstant;
import com.hicorp.segment.utils.ResultBean;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @Author: wqs
 * @Date: Created in 3:43 2021/5/19
 * @Description:
 * @ChineseDescription:
 * @Modified_By:
 */
@Slf4j
@RestController
@RequestMapping("/user")
@ApiVersion
//@Tag(name = "用户信息管理", description = "注册用户、修改密码等操作")
@Api(tags = "用户管理")
public class UserController {
    private final SecurityUtils securityUtils;
    protected final UserService userService;
    protected final UserInfoService userInfoService;

    public UserController(SecurityUtils securityUtils, UserService userService, UserInfoService userInfoService) {
        this.securityUtils = securityUtils;
        this.userService = userService;
        this.userInfoService = userInfoService;
    }

    //修改密码
    @Operation(summary = "修改密码", description = "修改密码")
    @Parameter(name = "changePassword", description = "原密码", required = true)
    @PostMapping("/changePassword/list")
    public ResultBean<Integer> changePasswordList(@RequestBody PasswordDO passwordDO) {
        Assert.notNull(passwordDO, "修改密码的数据不能为空!");
        String userName = securityUtils.getUserName();
        User user = userService.getUser(userName);
        //判断输入的原密码和加密后的密码是否一致
        BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
        boolean matches = bc.matches(passwordDO.getOldPassword(), user.getPassword());
        if (matches) {
            try {
                user.setGmtModified(new Date());
                PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                user.setPassword(passwordEncoder.encode(passwordDO.getNewPassword()));
                System.out.println(user);
                return userService.updateRecord(user);
            } catch (Exception e) {
                log.error(e.getMessage());
                return new ResultBean<>(-1, 400, "原密码正确，但修改密码失败!");
            }
        } else {
            return new ResultBean<>(-1, 400, "原密码错误，修改密码失败!");
        }
    }

    //找回密码     验证身份后修改密码
    @Operation(summary = "找回密码", description = "找回密码")
    @Parameter(name = "setPassword", description = "找回密码", required = true)
    @PostMapping("/setPassword/list")
    public ResultBean<Integer> setPasswordList(@RequestBody PasswordFindDO passwordFindDO) {
        Assert.notNull(passwordFindDO, "找回密码的数据不能为空!");
        User user = userService.getUser(passwordFindDO.getStaffNumber());
        UserInfo userInfo = userInfoService.getUserInfo(passwordFindDO.getStaffNumber());
        System.out.println(user);
        System.out.println(userInfo);
        if (user != null && userInfo != null) {
            if (userInfo.getName().equals(passwordFindDO.getName()) && userInfo.getPhoneNumber().equals(passwordFindDO.getPhoneNumber()) && userInfo.getIdCard().equals(passwordFindDO.getIdCard())) {
                try {
                    user.setGmtModified(new Date());
                    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                    user.setPassword(passwordEncoder.encode(passwordFindDO.getNewPassword()));
                    return userService.updateRecord(user);
                } catch (Exception e) {
                    log.error(e.getMessage());
                    return new ResultBean<>(-1, 400, "身份验证成功，但修改密码失败!");
                }
            } else {
                return new ResultBean<>(-1, 400, "验证身份信息失败，未能修改密码!");
            }
        }else {return new ResultBean<>(-1, 400, "公司没有该员工!");}
    }


    @Operation(summary = "获取用户信息", description = "带参数为分页查询用户，不带为查询所有")
    @GetMapping("/list")
    public ResultBean<List<User>> getUserList() {
        return userService.queryInfo();
    }

    @Operation(summary = "查询用户信息", description = "带参数为分页查询用户，不带为查询所有")
    @GetMapping(value = "/list", params = {"page", "size", "sort"})
    public ResultBean<PageInfo<User>> getRoleList(@RequestParam("page") Integer page,
                                                  @RequestParam("size") Integer size,
                                                  @RequestParam("sort") String sort) {
        return userService.queryInfo(page, size, sort);
    }

    @ApiVersion(value = {ApiVersionConstant.VERSION_1_0_0, ApiVersionConstant.VERSION_1_0_1})
    @Operation(summary = "新增单条用户", description = "需要是员工，才能存上")
    @Parameter(name = "user", description = "用户实体", required = true)
    @PostMapping("/add")
    public ResultBean<Integer> addUser(@RequestBody User user) {
        user.setGmtCreate(new Date());
        return userService.createRecord(user);
    }

    @Operation(summary = "批量新增用户", description = "根据用户实体创建用户")
    @Parameter(name = "users", description = "用户实体", required = true)
    @PostMapping("/addList")
    public ResultBean<Integer> addUserList(@RequestBody List<User> users) {
        Date date = new Date();
        users.forEach(user -> {
            user.setGmtCreate(date);
        });
        return userService.createRecords(users);
    }

    @ApiVersion(value = {ApiVersionConstant.VERSION_1_0_0, ApiVersionConstant.VERSION_1_0_1})
    @Operation(summary = "删除用户", description = "根据用户id删除用户")
    @Parameter(name = "id", description = "用户id", required = true)
    @DeleteMapping("/{id}")
    public ResultBean<Integer> deleteUser(@PathVariable(value = "id") Long id) {
        return userService.deleteRecord(id);
    }

    @Operation(summary = "批量删除", description = "根据用户id删除用户")
    @Parameter(name = "id", description = "用户id", required = true)
    @PostMapping("/deleteList")
    public ResultBean<Integer> deleteUserList(@RequestBody List<Object> ids) {
        return userService.deleteRecords("id", ids);
    }

    @Operation(summary = "更新用户", description = "根据用户id更新用户")
    @Parameters({
            @Parameter(name = "id", description = "用户id", required = true),
            @Parameter(name = "user", description = "用户实体", required = true)})
    @PutMapping("/{id}")
    public ResultBean<Integer> updateUser(@PathVariable(value = "id") Long id, @RequestBody User user) {
        return userService.updateRecord(user);
    }
}
