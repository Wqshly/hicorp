package com.hicorp.segment.mapper;

import com.hicorp.segment.mapper.basic.BasicMapper;
import com.hicorp.segment.pojo.RoleMenuRelation;

import java.util.List;

/**
 * @Author:
 * @Date: Created in 12:49 2021/6/1
 * @Description:
 * @ChineseDescription:
 * @Modified_By:
 */
public interface RoleMenuRelationMapper extends BasicMapper<RoleMenuRelation> {
    List<Long> selectMenuIdByRoleId(Long roleId);

    void deleteByRoleId(Long roleId);
}
