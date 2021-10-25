package com.hicorp.segment.mapper;

import com.hicorp.segment.mapper.basic.BasicMapper;
import com.hicorp.segment.pojo.RolePermissionRelation;

import java.util.List;

/**
 * @Author:
 * @Date: Created in 12:49 2021/6/1
 * @Description:
 * @ChineseDescription:
 * @Modified_By:
 */
public interface RolePermissionRelationMapper extends BasicMapper<RolePermissionRelation> {

    List<Long> selectPermissionIdByRoleId(Long roleId);

    void deleteByRoleId(Long roleId);
}
