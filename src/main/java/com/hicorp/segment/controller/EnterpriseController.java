package com.hicorp.segment.controller;

import com.github.pagehelper.PageInfo;
import com.hicorp.segment.pojo.Department;
import com.hicorp.segment.security.utils.SecurityUtils;
import com.hicorp.segment.service.DepartmentService;
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
 * @Date: Created in 16:43 2021/6/10
 * @Description:
 * @ChineseDescription:
 * @Modified_By:
 */
@RestController
@Api(tags = "企业管理")
public class EnterpriseController {

    private final SecurityUtils securityUtils;
    private final DepartmentService departmentService;

    public EnterpriseController(SecurityUtils securityUtils, DepartmentService departmentService) {
        this.securityUtils = securityUtils;
        this.departmentService = departmentService;
    }

    // 部门管理
    @Operation(summary = "获取部门信息", description = "带参数为分页查询用户，不带为查询所有")
    @GetMapping("/department/list")
    public ResultBean<List<Department>> getDepartmentList() {
        return departmentService.queryInfo();
    }

    @Operation(summary = "查询部门信息", description = "带参数为分页查询用户，不带为查询所有")
    @GetMapping(value = "/department/list", params = {"page", "size", "sort"})
    public ResultBean<PageInfo<Department>> getDepartmentList(@RequestParam("page") Integer page,
                                                              @RequestParam("size") Integer size,
                                                              @RequestParam("sort") String sort) {
        return departmentService.queryInfo(page, size, sort);
    }

    @Operation(summary = "查询部门信息", description = "带参数为分页查询角色信息，不带为查询所有")
    @GetMapping(value = "/department/list/{field}/{content}", params = {"page", "size", "sort"})
    public ResultBean<PageInfo<Department>> getDepartmentList(@RequestParam("page") Integer page,
                                                              @RequestParam("size") Integer size,
                                                              @RequestParam("sort") String sort,
                                                              @PathVariable("field") String field,
                                                              @PathVariable("content") String content) {
        return departmentService.selectRecordsByPage(page, size, sort, field, content);
    }

    @ApiVersion(value = {VERSION_1_0_0, VERSION_1_0_1})
    @Operation(summary = "新增单个部门", description = "需要是员工，才能存上")
    @Parameter(name = "department", description = "用户实体", required = true)
    @PostMapping("/department/add")
    public ResultBean<Integer> addDepartment(@RequestBody Department department) {
        Date date = new Date();
        department.setCreateUser(securityUtils.getUserName());
        department.setGmtCreate(date);
        try {
            return departmentService.createRecord(department);
        } catch (IllegalArgumentException e) {
            return new ResultBean<>(-1, 400, e.getMessage());
        } catch (Exception e) {
            return new ResultBean<>(-1, 403, "添加失败，请检查您的登录状态!");
        }
    }


    @Operation(summary = "批量新增部门", description = "根据用户实体创建用户")
    @Parameter(name = "departments", description = "用户实体", required = true)
    @PostMapping("/department/addList")
    public ResultBean<Integer> addDepartmentList(@RequestBody List<Department> departments) {
        String userName = securityUtils.getUserName();
        Date date = new Date();
        departments.forEach(department -> {
            department.setCreateUser(userName);
            department.setGmtCreate(date);
        });
        return departmentService.createRecords(departments);
    }


    @ApiVersion(value = {VERSION_1_0_0, VERSION_1_0_1})
    @Operation(summary = "删除用户", description = "根据用户id删除用户")
    @Parameter(name = "id", description = "用户id", required = true)
    @DeleteMapping("/department/{id}")
    public ResultBean<Integer> deleteDepartment(@PathVariable(value = "id") Long id) {
        return departmentService.deleteRecord(id);
    }

    @Operation(summary = "批量删除", description = "根据用户id删除部门")
    @Parameter(name = "id", description = "用户id", required = true)
    @PostMapping("/department/deleteList")
    public ResultBean<Integer> deleteDepartmentList(@RequestBody List<Object> ids) {
        return departmentService.deleteRecords("id", ids);
    }

    @Operation(summary = "更新部门", description = "根据部门id更新部门信息")
    @Parameters({
            @Parameter(name = "id", description = "用户id", required = true),
            @Parameter(name = "department", description = "部门实体", required = true)})
    @PutMapping("/department/{id}")
    public ResultBean<Integer> updateDepartment(@PathVariable(value = "id") Long id, @RequestBody Department department) {
        Date date = new Date();
        department.setId(id);
        department.setModifiedUser(securityUtils.getUserName());
        department.setGmtModified(date);
        try {
            return departmentService.updateNotNull(department);
        } catch (Exception e) {
            return new ResultBean<>(-1, 403, "修改失败，请检查您的登录状态!");
        }
    }
}

