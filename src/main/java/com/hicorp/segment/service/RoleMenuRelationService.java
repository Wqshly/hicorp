package com.hicorp.segment.service;

import com.hicorp.segment.pojo.RoleMenuRelation;
import com.hicorp.segment.utils.ResultBean;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: wqs
 * @Date: Created in 18:30 2021/6/1
 * @Description:
 * @ChineseDescription:
 * @Modified_By:
 */
@Service
public interface RoleMenuRelationService  extends BasicInterface<RoleMenuRelation>{
    ResultBean<List<Long>> selectMenuIdByRoleId(Long id);

    ResultBean<Integer> changeRoleMenuRelation(Long roleId, List<Long> menuIds);
}
