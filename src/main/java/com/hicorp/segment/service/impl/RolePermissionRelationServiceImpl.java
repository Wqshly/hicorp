package com.hicorp.segment.service.impl;

import com.hicorp.segment.security.utils.SecurityUtils;
import com.hicorp.segment.mapper.RolePermissionRelationMapper;
import com.hicorp.segment.pojo.RolePermissionRelation;
import com.hicorp.segment.service.RolePermissionRelationService;
import com.hicorp.segment.utils.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author:
 * @Date: Created in 14:44 2021/6/3
 * @Description:
 * @ChineseDescription:
 * @Modified_By:
 */
@Service("rolePermissionRelationService")
@Slf4j
public class RolePermissionRelationServiceImpl extends BasicInterfaceImpl<RolePermissionRelation> implements RolePermissionRelationService {

    @Resource
    private RolePermissionRelationMapper rolePermissionRelationMapper;

    private final SecurityUtils securityUtils;

    public RolePermissionRelationServiceImpl(SecurityUtils securityUtils) {
        this.securityUtils = securityUtils;
    }

    @Override
    public ResultBean<List<Long>> getPermissionIdByRoleId(Long roleId) {
        Assert.notNull(roleId, "角色ID不能为空,请先选择角色!");
        try {
            List<Long> permissionIds = rolePermissionRelationMapper.selectPermissionIdByRoleId(roleId);
            return new ResultBean<>(permissionIds);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResultBean<>(400, "查询失败!");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultBean<Integer> changeRolePermissionRelation(Long roleId, List<Long> permissionIds) {
        int result = -1;
        Assert.notNull(roleId, "角色ID不能为空!");
        Assert.isTrue(permissionIds.size() > 0, "你不能清空一个角色的所有权限，如需不需要该角色，请直接删除掉。");
        Date date = new Date();
        List<RolePermissionRelation> rolePermissionRelations = new ArrayList<>();
        rolePermissionRelationMapper.deleteByRoleId(roleId);
        permissionIds.forEach(permissionId -> {
            RolePermissionRelation rolePermissionRelation = new RolePermissionRelation(roleId, permissionId, securityUtils.getUserName(), date);
            rolePermissionRelations.add(rolePermissionRelation);
        });
        try {
            result = rolePermissionRelationMapper.insertList(rolePermissionRelations);
            return new ResultBean<>(result);
        } catch (Exception e) {
            log.error(e.getMessage());
            // 手动回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResultBean<>(-1, 400, "更新失败");
        }
    }
}
