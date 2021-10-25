package com.hicorp.segment.service.impl;

import com.hicorp.segment.pojo.MaintenanceRecordDetails;
import com.hicorp.segment.service.MaintenanceRecordDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;


@Service("MaintenanceRecordDetailsService")
public class MaintenanceRecordDetailsServiceImpl extends BasicInterfaceImpl<MaintenanceRecordDetails> implements MaintenanceRecordDetailsService {
    @Override
    public Void dataValid(MaintenanceRecordDetails maintenanceRecordDetails) {
        Assert.notNull(maintenanceRecordDetails.getClassificationId(), "维护类型id不能为空!");
        Assert.notNull(maintenanceRecordDetails.getConfigId(), "项目配置id不能为空!");
        Assert.notNull(maintenanceRecordDetails.getViewId(), "记录查看id不能为空!");
        Assert.notNull(maintenanceRecordDetails.getResult(), "结果不能为空!");
        return null;
    }
}