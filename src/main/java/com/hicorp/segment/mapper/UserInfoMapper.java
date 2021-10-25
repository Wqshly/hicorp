package com.hicorp.segment.mapper;

import com.hicorp.segment.mapper.basic.BasicMapper;
import com.hicorp.segment.pojo.UserInfo;

import java.util.List;


public interface UserInfoMapper extends BasicMapper<UserInfo> {
    UserInfo findByStaffNumber(String staffNumber);

    Long findUserIdByStaffNumber(String staffNumber);

    @Override
    List<UserInfo> selectAll();
}