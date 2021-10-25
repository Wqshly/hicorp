package com.hicorp.segment.service.impl;

import com.hicorp.segment.mapper.DeviceCategoryMapper;
import com.hicorp.segment.mapper.DeviceInfoMapper;
import com.hicorp.segment.pojo.DeviceInfo;
import com.hicorp.segment.service.DeviceInfoService;
import com.hicorp.segment.utils.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.Assert;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("deviceInfoService")
@Slf4j
public class DeviceInfoServiceImpl extends BasicInterfaceImpl<DeviceInfo> implements DeviceInfoService {
    @Resource
    private DeviceInfoMapper deviceInfoMapper;

    @Resource
    private DeviceCategoryMapper deviceCategoryMapper;

    @Override
    public Void dataValid(DeviceInfo deviceInfo) {
        Assert.notNull(deviceInfo.getName(), "产品名称不能为空!");
        Assert.notNull(deviceInfo.getNumber(), "编号不能为空!");
        Assert.notNull(deviceInfo.getAddress(), "位置不能为空!");
        Assert.notNull(deviceInfo.getStatus(), "状态不能为空!");
        Assert.notNull(deviceInfo.getGmtCreate(), "创建时间不能为空!");
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)   //回滚
    public ResultBean<Integer> createRecord(DeviceInfo deviceInfo) {
        int callback = -1;
        this.dataValid(deviceInfo);
        // 数据校验
        try {
            //抓是否新增    deviceInfoMapper.insert(deviceInfo)默认返还1
            callback = deviceInfoMapper.insert(deviceInfo);
        } catch (Exception e) {
            log.error(e.getMessage());
            // 手动回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            if (e.getCause() instanceof PersistenceException) {
                return new ResultBean<>(-1, 400, "数据库连接失败!");
            }
            if (e.getCause() instanceof SQLIntegrityConstraintViolationException) {
                return new ResultBean<>(-1, 400, "添加失败，编号重复!");
            }
        }
        if (callback != -1) {
            int newNumber;
            newNumber = deviceInfoMapper.countNumber(deviceInfo.getName());
            try {
                callback = deviceCategoryMapper.updateNumber(deviceInfo.getName(), newNumber);
            } catch (Exception e) {
                log.error(e.getMessage());
                // 手动回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return new ResultBean<>(-1, 400, "添加成功，但修改总数量时失败!");
            }
        }
        return new ResultBean<>(callback);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)   //回滚
    public ResultBean<Integer> createRecords(List<DeviceInfo> deviceInfos) {
        Assert.notNull(deviceInfos, "数据不能为空!");
        int callback = -1;
        Map<String, Boolean> deviceInfoMap = new HashMap<>();
        try {
            //抓是否新增    deviceInfosMapper.insertList(deviceInfos)默认返还1
            callback = deviceInfoMapper.insertList(deviceInfos);
        } catch (Exception e) {
            log.error(e.getMessage());
            // 手动回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResultBean<>(-1, 400, "批量添加失败，请保证对应模板且唯一值不重复!");
        }
        if (callback != -1) {
            //批量增添成功时，用哈希映射将导入的列表中的数据的名称不重复的取出，将这些名称的数据的数量更新一次
            for (DeviceInfo deviceInfo :
                    deviceInfos) {
                if (!deviceInfoMap.containsKey(deviceInfo.getName())) {
                    int newNumber;
                    newNumber = deviceInfoMapper.countNumber(deviceInfo.getName());
                    try {
                        callback = deviceCategoryMapper.updateNumber(deviceInfo.getName(), newNumber);
                    } catch (Exception e) {
                        log.error(e.getMessage());
                        // 手动回滚
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return new ResultBean<>(-1, 400, "批量添加成功，但修改总数量时失败!");
                    }
                    // 执行相关的查询、更新
                    deviceInfoMap.put(deviceInfo.getName(), true);
                }
            }

        }
        return new ResultBean<>(callback);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultBean<Integer> deleteRecord(Object value) {
        Assert.notNull(value, "数据不能为空!");
        int callback = -1;
        int id = (int) value;
        String name = deviceInfoMapper.selectNameById(id);
        try {
            callback = basicMapper.deleteByPrimaryKey(value);
        } catch (Exception e) {
            log.error(e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            if (e.getCause() instanceof PersistenceException) {
                return new ResultBean<>(-1, 400, "数据库连接失败!");
            }
            return new ResultBean<>(400, "删除失败!");
        }
        if (callback != -1) {
            int newNumber;
            newNumber = deviceInfoMapper.countNumber(name);
            try {
                callback = deviceCategoryMapper.updateNumber(name, newNumber);
            } catch (Exception e) {
                log.error(e.getMessage());
                // 手动回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return new ResultBean<>(-1, 400, "删除成功，但修改总数量时失败!");
            }
        }
        return new ResultBean<>(callback);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultBean<Integer> deleteRecords(String fieldName, List<Object> values) {
        int result = -1;
        Assert.notNull(values, "数据不能为空!");
        Map<String, Boolean> deviceInfoMapTwo = new HashMap<>();
        // 声明Example
        Example example = new Example(this.clazz);
        // 获取条件对象
        Example.Criteria criteria = example.createCriteria();
        // 设置条件,第一个是pojo的属性名,第二个参数是条件
        criteria.andIn(fieldName, values);
        //用哈希映射将导入的列表中的数据的名称不重复的取出,放入name列表中
        List<String> names = new ArrayList<>();
        for (Object value :
                values) {
            int id = (int) value;
            if (!deviceInfoMapTwo.containsKey(deviceInfoMapper.selectNameById(id))) {
                String name = deviceInfoMapper.selectNameById(id);
                names.add(name);
                deviceInfoMapTwo.put(name, true);
            }
        }
        // 执行批量删除
        try {
            result = basicMapper.deleteByExample(example);
        } catch (Exception e) {
            log.error(e.getMessage());
            // 手动回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            if (e.getCause() instanceof PersistenceException) {
                return new ResultBean<>(-1, 400, "数据库连接失败!");
            }
            return new ResultBean<>(400, "批量删除失败!");
        }
        if (result != -1) {
            //批量删除成功时，将这些名称的数据的数量更新一次
            for (String name :
                    names) {
                int newNumber;
                newNumber = deviceInfoMapper.countNumber(name);
                try {
                    result = deviceCategoryMapper.updateNumber(name, newNumber);
                } catch (Exception e) {
                    log.error(e.getMessage());
                    // 手动回滚
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return new ResultBean<>(-1, 400, "批量添加成功，但修改总数量时失败!");
                }
                // 执行相关的查询、更新
                deviceInfoMapTwo.put(name, true);
            }
        }
        return new ResultBean<>(result);
    }
    @Override
    public ResultBean<List<DeviceInfo>> getDeviceInfoListByName(String name) {
        List<DeviceInfo> deviceInfoList = deviceInfoMapper.selectByName(name);
        log.info(String.valueOf(deviceInfoList));
        return new ResultBean<>(deviceInfoList);
    }
}
