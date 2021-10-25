package com.hicorp.segment.service.impl;

import com.hicorp.segment.mapper.UserInfoMapper;
import com.hicorp.segment.mapper.UserMapper;
import com.hicorp.segment.pojo.User;
import com.hicorp.segment.pojo.UserInfo;
import com.hicorp.segment.service.UserInfoService;
import com.hicorp.segment.utils.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.util.List;

/**
 * @Author:
 * @Date: Created in 17:08 2021/5/23
 * @Description:
 * @ChineseDescription:
 * @Modified_By:
 */
@Slf4j
@Service("userInfoService")
public class UserInfoServiceImpl extends BasicInterfaceImpl<UserInfo> implements UserInfoService {
    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private UserMapper userMapper;

    @Override
    public Void dataValid(UserInfo userInfo) {
        Assert.notNull(userInfo.getName(), "员工姓名不能为空!");
        Assert.notNull(userInfo.getStaffNumber(), "员工编号不能为空!");
        Assert.notNull(userInfo.getPhoneNumber(), "电话号码不能为空!");
        Assert.notNull(userInfo.getIdCard(), "身份证号不能为空!");
        Assert.notNull(userInfo.getSex(), "性别不能为空!");
        Assert.notNull(userInfo.getDepartment(), "所属部门不能为空!");
        Assert.notNull(userInfo.getGmtCreate(), "创建时间不能为空!");
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)   //回滚
    public ResultBean<Integer> createRecord(UserInfo userInfo) {
        int callback = -1;
        int callbackUser = -1;
        this.dataValid(userInfo);
        // 数据校验
        User user = new User();
        Date date = new Date();
        try {
            callback = userInfoMapper.insert(userInfo);
        } catch (Exception e) {
            log.error(e.getMessage());
            // 手动回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            if (e.getCause() instanceof PersistenceException) {
                return new ResultBean<>(-1, 400, "添加失败,数据库连接失败!");
            }
            if (e.getCause() instanceof SQLIntegrityConstraintViolationException) {
                return new ResultBean<>(-1, 400, "添加失败，员工编号重复!");
            }
            return new ResultBean<>(-1, 400, "添加失败!");
        }
        if (callback != -1) {
            try {
                user.setGmtCreate(date);
                PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                user.setPassword(passwordEncoder.encode(userInfo.getPhoneNumber()));
                user.setUserName(userInfo.getStaffNumber());
                callbackUser = userMapper.insert(user);
            } catch (Exception e) {
                log.error(e.getMessage());
                // 手动回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return new ResultBean<>(-1, 400, "添加员工信息成功，但创建账号失败!");
            }
        }
        return new ResultBean<>(callbackUser);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)   //回滚
    public ResultBean<Integer> createRecords(List<UserInfo> userInfos) {
        Assert.notNull(userInfos, "数据不能为空!");
        User user = new User();
        Date date = new Date();
        int callback = -1;
        int callbackUser = -1;
        try {
            //抓是否新增    deviceInfosMapper.insertList(deviceInfos)默认返还1
            callback = userInfoMapper.insertList(userInfos);
        } catch (Exception e) {
            log.error(e.getMessage());
            // 手动回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResultBean<>(-1, 400, "批量添加失败，请保证对应模板且唯一值不重复!");
        }
        if (callback != -1) {
            //批量增添成功时，用新增的员工信息创建多个账号
            for (UserInfo userInfo :
                    userInfos) {
                try {
                    user.setId(null);
                    user.setGmtCreate(date);
                    System.out.println(user.getGmtCreate());
                    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                    System.out.println(passwordEncoder);
                    user.setPassword(passwordEncoder.encode(userInfo.getPhoneNumber()));
                    System.out.println(user.getPassword());
                    user.setUserName(userInfo.getStaffNumber());
                    System.out.println(userInfo.getStaffNumber());
                    System.out.println(user.getUserName());
                    System.out.println(user);
                    callbackUser = userMapper.insert(user);
                    System.out.println(user);
                    System.out.println(callbackUser);
                } catch (Exception e) {
                    log.error(e.getMessage());
                    // 手动回滚
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return new ResultBean<>(-1, 400, "批量添加成功，但创建账号失败!");
                }
            }

        }
        return new ResultBean<>(callbackUser);
    }

    @Override
    public UserInfo getUserInfo(String staffNumber) {
        return userInfoMapper.findByStaffNumber(staffNumber);
    }
}
