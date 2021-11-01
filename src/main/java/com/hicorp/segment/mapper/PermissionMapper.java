package com.hicorp.segment.mapper;

import com.hicorp.segment.mapper.basic.BasicMapper;
import com.hicorp.segment.pojo.Permission;

import java.util.List;


public interface PermissionMapper extends BasicMapper<Permission> {
    List<Permission> findPermissionByUserId(long userId);
}