package com.hicorp.segment.mapper;

import com.hicorp.segment.mapper.basic.BasicMapper;


public interface UserMapper extends BasicMapper<User> {
    User findByUserName(String userName);
}