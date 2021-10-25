package com.hicorp.segment.controller;

import com.github.pagehelper.PageInfo;
import com.hicorp.segment.pojo.UserInfo;
import com.hicorp.segment.security.utils.SecurityUtils;
import com.hicorp.segment.service.UserInfoService;
import com.hicorp.segment.swagger.ApiVersion;
import com.hicorp.segment.utils.ResultBean;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import static com.hicorp.segment.swagger.ApiVersionConstant.VERSION_1_0_0;
import static com.hicorp.segment.swagger.ApiVersionConstant.VERSION_1_0_1;

/**
 * @Author: wqs
 * @Date: Created in 16:59 2021/5/23
 * @Description:
 * @ChineseDescription:
 * @Modified_By:
 */
@RestController
@ApiVersion
@Api(tags = "员工管理")
public class UserInfoController {

    private final SecurityUtils securityUtils;
    protected final UserInfoService userInfoService;

    public UserInfoController(SecurityUtils securityUtils, UserInfoService userInfoService) {
        this.securityUtils = securityUtils;
        this.userInfoService = userInfoService;
    }

    // 巡检点管理
    @Operation(summary = "获取员工信息", description = "带参数为分页查询巡检点，不带为查询所有")
    @GetMapping("/userInfo/list")
    public ResultBean<List<UserInfo>> getUserInfoList() {
        return userInfoService.queryInfo();
    }

    @Operation(summary = "查询员工信息", description = "带参数为分页查询巡检点，不带为查询所有")
    @GetMapping(value = "/userInfo/list", params = {"page", "size", "sort"})
    public ResultBean<PageInfo<UserInfo>> getUserInfoList(@RequestParam("page") Integer page,
                                                                @RequestParam("size") Integer size,
                                                                @RequestParam("sort") String sort) {
        return userInfoService.queryInfo(page, size, sort);
    }

    @Operation(summary = "查询员工信息", description = "带参数为分页查询员工信息，不带为查询所有")
    @GetMapping(value = "/userInfo/list/{field}/{content}", params = {"page", "size", "sort"})
    public ResultBean<PageInfo<UserInfo>> getUserInfoList(@RequestParam("page") Integer page,
                                                                @RequestParam("size") Integer size,
                                                                @RequestParam("sort") String sort,
                                                                @PathVariable("field") String field,
                                                                @PathVariable("content") String content) {
        return userInfoService.selectRecordsByPage(page, size, sort, field, content);
    }

    @ApiVersion(value = {VERSION_1_0_0, VERSION_1_0_1})
    @Operation(summary = "新增单个员工信息", description = "需要是员工信息，才能存上")
    @Parameter(name = "userInfo", description = "员工实体", required = true)
    @PostMapping("/userInfo/add")
    public ResultBean<Integer> addUserInfo(@RequestBody UserInfo userInfo) {
        Date date = new Date();
        userInfo.setCreateUser(securityUtils.getUserName());
        userInfo.setGmtCreate(date);
        try {
            return userInfoService.createRecord(userInfo);
        } catch (IllegalArgumentException e) {
            return new ResultBean<>(-1, 400, e.getMessage());
        } catch (Exception e) {
            return new ResultBean<>(-1, 403, "添加失败，请检查您的登录状态!");
        }
    }


    @Operation(summary = "批量新增员工信息", description = "根据员工实体创建员工信息")
    @Parameter(name = "userInfos", description = "员工实体", required = true)
    @PostMapping("/userInfo/addList")
    public ResultBean<Integer> addUserInfoList(@RequestBody List<UserInfo> userInfos) {
        String userName = securityUtils.getUserName();
        Date date = new Date();
        userInfos.forEach(userInfo -> {
            userInfo.setCreateUser(userName);
            userInfo.setGmtCreate(date);
        });
        return userInfoService.createRecords(userInfos);
    }


    @ApiVersion(value = {VERSION_1_0_0, VERSION_1_0_1})
    @Operation(summary = "删除员工信息", description = "根据员工id删除员工信息")
    @Parameter(name = "id", description = "员工信息id", required = true)
    @DeleteMapping("/userInfo/{id}")
    public ResultBean<Integer> deleteUserInfo(@PathVariable(value = "id") Long id) {
        return userInfoService.deleteRecord(id);
    }

    @Operation(summary = "批量删除员工信息", description = "根据员工信息id批量删除员工信息")
    @Parameter(name = "id", description = "员工信息id", required = true)
    @PostMapping("/userInfo/deleteList")
    public ResultBean<Integer> deleteUserInfoList(@RequestBody List<Object> ids) {
        return userInfoService.deleteRecords("id", ids);
    }

    @Operation(summary = "更新员工信息", description = "根据员工信息id更新员工信息")
    @Parameters({
            @Parameter(name = "id", description = "员工信息id", required = true),
            @Parameter(name = "userInfo", description = "员工实体", required = true)})
    @PutMapping("/userInfo/{id}")
    public ResultBean<Integer> updateUserInfo(@PathVariable(value = "id") Long id, @RequestBody UserInfo userInfo) {
        Date date = new Date();
        userInfo.setId(id);
        userInfo.setModifiedUser(securityUtils.getUserName());
        userInfo.setGmtModified(date);
        try {
            return userInfoService.updateNotNull(userInfo);
        } catch (Exception e) {
            return new ResultBean<>(-1, 403, "修改失败，请检查您的登录状态!");
        }
    }


}
