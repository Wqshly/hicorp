package com.hicorp.segment.service.impl;

import com.hicorp.segment.security.utils.SecurityUtils;
import com.hicorp.segment.mapper.RoleMenuRelationMapper;
import com.hicorp.segment.pojo.RoleMenuRelation;
import com.hicorp.segment.service.RoleMenuRelationService;
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
 * @Date: Created in 18:31 2021/6/1
 * @Description:
 * @ChineseDescription:
 * @Modified_By:
 */
@Service("roleMenuRelationService")
@Slf4j
public class RoleMenuRelationServiceImpl extends BasicInterfaceImpl<RoleMenuRelation> implements RoleMenuRelationService {
    @Resource
    private RoleMenuRelationMapper roleMenuRelationMapper;

    private final SecurityUtils securityUtils;

    public RoleMenuRelationServiceImpl(SecurityUtils securityUtils) {
        this.securityUtils = securityUtils;
    }

    @Override
    public ResultBean<List<Long>> selectMenuIdByRoleId(Long id) {
        try {
            List<Long> ids = roleMenuRelationMapper.selectMenuIdByRoleId(id);
            return new ResultBean<>(ids);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResultBean<>(400, "查询失败!");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultBean<Integer> changeRoleMenuRelation(Long roleId, List<Long> menuIds) {
        int result = -1;
        Assert.notNull(roleId, "角色ID不能为空!");
        Assert.isTrue(menuIds.size() > 0, "你不能删除该角色所有的菜单，如需删除角色，请勾选并点击删除按钮!");
        Date date = new Date();
        List<RoleMenuRelation> roleMenuRelations = new ArrayList<>();
        roleMenuRelationMapper.deleteByRoleId(roleId);
        menuIds.forEach(menuId -> {
            RoleMenuRelation roleMenuRelation = new RoleMenuRelation(roleId, menuId, securityUtils.getUserName(), date);
            roleMenuRelations.add(roleMenuRelation);
        });
        try {
            result = roleMenuRelationMapper.insertList(roleMenuRelations);
            return new ResultBean<>(result);
        } catch (Exception e) {
            log.error(e.getMessage());
            // 手动回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResultBean<>(-1, 400, "更新失败");
        }
    }
}
