package com.hicorp.segment.mapper;

import com.hicorp.segment.mapper.basic.BasicMapper;
import com.hicorp.segment.pojo.UserRoleRelation;

import java.util.List;

/**
 * @Author:
 * @Date: Created in 12:49 2021/6/1
 * @Description:
 * @ChineseDescription:
 * @Modified_By:
 */
public interface UserRoleRelationMapper extends BasicMapper<UserRoleRelation> {
    List<Long> selectRoleIdByUserId(Long userId);

    void deleteByUserId(Long userId);
}
