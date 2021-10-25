package com.hicorp.segment.service.impl;

import com.hicorp.segment.security.utils.SecurityUtils;
import com.hicorp.segment.mapper.UserRoleRelationMapper;
import com.hicorp.segment.pojo.UserRoleRelation;
import com.hicorp.segment.service.UserRoleRelationService;
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
 * @Date: Created in 17:32 2021/6/6
 * @Description:
 * @ChineseDescription:
 * @Modified_By:
 */
@Service("UserRoleRelationService")
@Slf4j
public class UserRoleRelationServiceImpl extends BasicInterfaceImpl<UserRoleRelation> implements UserRoleRelationService {

    @Resource
    private UserRoleRelationMapper userRoleRelationMapper;

    private final SecurityUtils securityUtils;

    public UserRoleRelationServiceImpl(SecurityUtils securityUtils) {
        this.securityUtils = securityUtils;
    }

    @Override
    public ResultBean<List<Long>> selectRoleIdByUserId(Long userId) {
        Assert.notNull(userId, "用户ID不能为空,请先选择用户!");
        try {
            List<Long> roleIds = userRoleRelationMapper.selectRoleIdByUserId(userId);
            return new ResultBean<>(roleIds);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResultBean<>(400, "查询失败!");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultBean<Integer> changeUserRoleRelation(Long userId, List<Long> roleIds) {
        int result = -1;
        Assert.notNull(userId, "角色ID不能为空!");
        Assert.isTrue(roleIds.size() > 0, "你不能删除该用户所有角色!");
        Date date = new Date();
        List<UserRoleRelation> userRoleRelations = new ArrayList<>();
        userRoleRelationMapper.deleteByUserId(userId);
        roleIds.forEach(roleId -> {
            UserRoleRelation roleMenuRelation = new UserRoleRelation(userId, roleId, securityUtils.getUserName(), date);
            userRoleRelations.add(roleMenuRelation);
        });
        try {
            result = userRoleRelationMapper.insertList(userRoleRelations);
            return new ResultBean<>(result);
        } catch (Exception e) {
            log.error(e.getMessage());
            // 手动回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResultBean<>(-1, 400, "更新失败");
        }
    }
}
