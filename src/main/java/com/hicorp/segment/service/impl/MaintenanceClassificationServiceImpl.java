package com.hicorp.segment.service.impl;

import com.hicorp.segment.pojo.MaintenanceClassification;
import com.hicorp.segment.service.MaintenanceClassificationService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service("MaintenanceClassificationService")
public class MaintenanceClassificationServiceImpl extends BasicInterfaceImpl<MaintenanceClassification> implements MaintenanceClassificationService {
    @Override
    public Void dataValid(MaintenanceClassification maintenanceClassification) {
        Assert.notNull(maintenanceClassification.getName(), "类型名称不能为空!");
        Assert.notNull(maintenanceClassification.getUseStatus(), "使用状态不能为空!");
        return null;
    }
}
