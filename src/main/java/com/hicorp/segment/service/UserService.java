package com.hicorp.segment.service;

import com.hicorp.segment.pojo.User;
import org.springframework.stereotype.Service;

/**
 * @Author: wqs
 * @Date: Created in 4:24 2021/5/19
 * @Description:
 * @ChineseDescription:
 * @Modified_By:
 */
@Service
public interface UserService extends BasicInterface<User> {
    User getUser(String userName);
}
