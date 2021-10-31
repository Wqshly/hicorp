package com.hicorp.segment.service;

import com.hicorp.segment.pojo.UserRoleRelation;
import com.hicorp.segment.utils.ResultBean;

import java.util.List;

/**
 * @Author: wqs
 * @Date: Created in 17:32 2021/6/6
 * @Description:
 * @ChineseDescription:
 * @Modified_By:
 */
public interface UserRoleRelationService extends BasicInterface<UserRoleRelation> {
    ResultBean<List<Long>> selectRoleIdByUserId(Long userId);

    ResultBean<Integer> changeUserRoleRelation(Long userId, List<Long> roleIds);
}
