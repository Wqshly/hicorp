package com.hicorp.segment.controller;

import com.github.pagehelper.PageInfo;
import com.hicorp.segment.pojo.Menu;
import com.hicorp.segment.pojo.Permission;
import com.hicorp.segment.pojo.Role;
import com.hicorp.segment.security.utils.SecurityUtils;
import com.hicorp.segment.service.*;
import com.hicorp.segment.utils.ResultBean;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @Author: wqs
 * @Date: Created in 15:51 2021/5/23
 * @Description:
 * @ChineseDescription:
 * @Modified_By:
 */
@RestController
@RequestMapping("/permission")
@Api(tags = "权限管理")
public class PermissionController {

    private final SecurityUtils securityUtils;
    private final PermissionService permissionService;
    private final RoleService roleService;
    private final MenuService menuService;
    private final RoleMenuRelationService roleMenuRelationService;
    private final RolePermissionRelationService rolePermissionRelationService;
    private final UserRoleRelationService userRoleRelationService;

    public PermissionController(PermissionService permissionService, RoleService roleService, MenuService menuService, RoleMenuRelationService roleMenuRelationService, RolePermissionRelationService rolePermissionRelationService, UserRoleRelationService userRoleRelationService, SecurityUtils securityUtils) {
        this.permissionService = permissionService;
        this.roleService = roleService;
        this.menuService = menuService;
        this.roleMenuRelationService = roleMenuRelationService;
        this.rolePermissionRelationService = rolePermissionRelationService;
        this.userRoleRelationService = userRoleRelationService;
        this.securityUtils = securityUtils;
    }

    @Operation(summary = "查询角色信息", description = "带参数为分页查询角色信息，不带为查询所有")
    @GetMapping("/role/list")
    public ResultBean<List<Role>> getRoleList() {
        return roleService.queryInfo();
    }

    @Operation(summary = "查询角色信息", description = "带参数为分页查询角色信息，不带为查询所有")
    @GetMapping(value = "/role/list", params = {"page", "size", "sort"})
    public ResultBean<PageInfo<Role>> getRoleList(@RequestParam("page") Integer page,
                                                  @RequestParam("size") Integer size,
                                                  @RequestParam("sort") String sort) {
        return roleService.queryInfo(page, size, sort);
    }

    @Operation(summary = "查询角色信息", description = "带参数为分页查询角色信息，不带为查询所有")
    @GetMapping(value = "/role/list/{field}/{content}", params = {"page", "size", "sort"})
    public ResultBean<PageInfo<Role>> getRoleList(@RequestParam("page") Integer page,
                                                  @RequestParam("size") Integer size,
                                                  @RequestParam("sort") String sort,
                                                  @PathVariable("field") String field,
                                                  @PathVariable("content") String content) {
        return roleService.selectRecordsByPage(page, size, sort, field, content);
    }

    @Operation(summary = "添加一个新的角色", description = "在角色表中添加一个新的角色")
    @PostMapping("/role")
    public ResultBean<Integer> addRole(@RequestBody @Validated Role role) {
        try {
            Date date = new Date();
            role.setCreateUser(securityUtils.getUserName());
            role.setCreateGmt(date);
            return roleService.createRecord(role);
        } catch (Exception e) {
            return new ResultBean<>(-1, 403, "添加失败，请检查您的登录状态!");
        }
    }

    @Operation(summary = "添加角色(批量导入)", description = "用于前端的批量导入")
    @PostMapping("/roles")
    public ResultBean<Integer> addRoles(@RequestBody @Validated List<Role> roles) {
        try {
            String userName = securityUtils.getUserName();
            Date date = new Date();
            roles.forEach(role -> {
                role.setCreateUser(userName);
                role.setCreateGmt(date);
            });
            return roleService.createRecords(roles);
        } catch (Exception e) {
            return new ResultBean<>(-1, 403, "添加失败，请检查您的登录状态!");
        }
    }

    @Operation(summary = "修改角色信息", description = "null")
    @PutMapping("/role/{id}")
    public ResultBean<Integer> updateRole(@PathVariable Long id, @RequestBody Role role) {
        Date date = new Date();
        role.setModifiedUser(securityUtils.getUserName());
        role.setId(id);
        role.setModifiedGmt(date);
        try {
            return roleService.updateNotNull(role);
        } catch (Exception e) {
            return new ResultBean<>(-1, 403, "修改失败，请检查您的登录状态!");
        }
    }

    @PostMapping("/role/deleteList")
    public ResultBean<Integer> deleteRoleList(@RequestBody List<Object> ids) {
        if (ids.contains(1)) {
            return new ResultBean<>(-1, 400, "不能删除初始角色!");
        }
        return roleService.deleteRecords("id", ids);
    }



    @Operation(summary = "获取菜单树", description = "通过一个递归函数，生成菜单树")
    @GetMapping("/menu/allTree")
    public ResultBean<List<Menu>> getMenuTree() {
        return menuService.getAllMenuTree();
    }


    @Operation(summary = "获取所有权限", description = "获取系统中所有已经录入的 API 权限")
    @GetMapping("/permission/list")
    public ResultBean<List<Permission>> getAllPermission() {
        return permissionService.queryInfo();
    }

    @Operation(summary = "获取用户角色", description = "获取用户ID关联的角色ID")
    @GetMapping("/findRoleIdByUser/{userId}")
    public ResultBean<List<Long>> getRoleIdByUserId(@PathVariable(value = "userId") Long userId) {
        return userRoleRelationService.selectRoleIdByUserId(userId);
    }

    @Operation(summary = "获取角色菜单", description = "获取角色ID关联的菜单ID")
    @GetMapping("/findMenuIdByRole/{roleId}")
    public ResultBean<List<Long>> getMenuTreeById(@PathVariable(value = "roleId") Long roleId) {
        return roleMenuRelationService.selectMenuIdByRoleId(roleId);
    }

    @Operation(summary = "获取角色权限", description = "获取选中的角色的ID所对应权限的ID，传值到前端")
    @GetMapping("/findPermissionIdByRole/{roleId}")
    public ResultBean<List<Long>> getPermissionIdByRoleId(@PathVariable("roleId") Long roleId) {
        return rolePermissionRelationService.getPermissionIdByRoleId(roleId);
    }

    @Operation(summary = "改变用户对应的角色关系", description = "修改 user_role_relation 表")
    @PostMapping("/userRoleRelation/{userId}")
    public ResultBean<Integer> changeUserRoleRelation(@PathVariable("userId") Long userId, @RequestBody List<Long> roleIds) {
        try {
            return userRoleRelationService.changeUserRoleRelation(userId, roleIds);
        } catch (IllegalArgumentException e) {
            return new ResultBean<>(-1, 400, e.getMessage());
        } catch (Exception e) {
            return new ResultBean<>(-1, 403, "添加失败，请检查您的登录状态!");
        }
    }

    @Operation(summary = "改变角色对应的菜单访问权限", description = "修改 role_menu_relation 表")
    @PostMapping("/roleMenuRelation/{roleId}")
    public ResultBean<Integer> changeRoleMenuRelation(@PathVariable("roleId") Long roleId, @RequestBody List<Long> menuIds) {
        try {
            return roleMenuRelationService.changeRoleMenuRelation(roleId, menuIds);
        } catch (IllegalArgumentException e) {
            return new ResultBean<>(-1, 400, e.getMessage());
        } catch (Exception e) {
            return new ResultBean<>(-1, 403, "添加失败，请检查您的登录状态!");
        }
    }

    @Operation(summary = "改变角色对应的 API 权限", description = "修改 role_permission_relation 表")
    @PostMapping("/rolePermissionRelation/{roleId}")
    public ResultBean<Integer> changeRolePermissionRelation(@PathVariable("roleId") Long roleId, @RequestBody List<Long> permissionIds) {
        try {
            return rolePermissionRelationService.changeRolePermissionRelation(roleId, permissionIds);
        } catch (IllegalArgumentException e) {
            return new ResultBean<>(-1, 400, e.getMessage());
        } catch (Exception e) {
            return new ResultBean<>(-1, 403, "添加失败，请检查您的登录状态!");
        }
    }
}
