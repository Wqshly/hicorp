package com.hicorp.segment.service;

import com.hicorp.segment.pojo.UserInfo;

/**
 * @Author: wqs
 * @Date: Created in 17:07 2021/5/23
 * @Description:
 * @ChineseDescription:
 * @Modified_By:
 */
public interface UserInfoService extends BasicInterface<UserInfo> {
    UserInfo getUserInfo(String staff_number);
}
