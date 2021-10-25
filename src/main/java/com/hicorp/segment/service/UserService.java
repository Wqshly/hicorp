package com.hicorp.segment.service;

import com.hicorp.segment.pojo.User;

/**
 * @Author: wqs
 * @Date: Created in 4:24 2021/5/19
 * @Description:
 * @ChineseDescription:
 * @Modified_By:
 */

public interface UserService extends BasicInterface<User> {
    User getUser(String userName);
}
