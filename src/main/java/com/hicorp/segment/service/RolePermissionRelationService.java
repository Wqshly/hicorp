package com.hicorp.segment.service;

import com.hicorp.segment.pojo.RolePermissionRelation;
import com.hicorp.segment.utils.ResultBean;

import java.util.List;

/**
 * @Author: wqs
 * @Date: Created in 14:43 2021/6/3
 * @Description:
 * @ChineseDescription:
 * @Modified_By:
 */
public interface RolePermissionRelationService extends BasicInterface<RolePermissionRelation> {
    ResultBean<List<Long>> getPermissionIdByRoleId(Long roleId);

    ResultBean<Integer> changeRolePermissionRelation(Long roleId, List<Long> permissionIds);
}
