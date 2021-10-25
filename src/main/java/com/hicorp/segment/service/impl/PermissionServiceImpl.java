package com.hicorp.segment.service.impl;

import com.hicorp.segment.mapper.PermissionMapper;
import com.hicorp.segment.pojo.Permission;
import com.hicorp.segment.service.PermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author:
 * @Date: Created in 10:01 2021/5/19
 * @Description:
 * @ChineseDescription:
 * @Modified_By:
 */
@Service("permissionService")
public class PermissionServiceImpl extends BasicInterfaceImpl<Permission> implements PermissionService {

    @Resource
    private PermissionMapper permissionMapper;

}
