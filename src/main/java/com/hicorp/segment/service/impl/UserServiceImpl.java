package com.hicorp.segment.service.impl;

import com.hicorp.segment.mapper.UserMapper;
import com.hicorp.segment.pojo.User;
import com.hicorp.segment.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author:
 * @Date: Created in 4:24 2021/5/19
 * @Description:
 * @ChineseDescription:
 * @Modified_By:
 */
@Service("userService")
public class UserServiceImpl extends BasicInterfaceImpl<User> implements UserService {
    @Resource
    private UserMapper userMapper;
    @Override
    public User getUser(String userName) {
       return userMapper.findByUserName(userName);
    }

//    @Autowired(required = false)
//    public UserServiceImpl(BasicMapper<User> basicMapper) {
//        super(basicMapper);
//    }
}
