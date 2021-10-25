package com.hicorp.segment.mapper;

import com.hicorp.segment.mapper.basic.BasicMapper;
import com.hicorp.segment.pojo.User;


public interface UserMapper extends BasicMapper<User> {
    User findByUserName(String userName);
}