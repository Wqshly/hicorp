package com.hicorp.segment.service.impl;

import com.hicorp.segment.pojo.MaintenanceItemConfig;
import com.hicorp.segment.service.MaintenanceItemConfigService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service("MaintenanceItemConfigService")
public class MaintenanceItemConfigServiceImpl extends BasicInterfaceImpl<MaintenanceItemConfig> implements MaintenanceItemConfigService {
    @Override
    public Void dataValid(MaintenanceItemConfig maintenanceItemConfig) {
        Assert.notNull(maintenanceItemConfig.getClassificationId(), "维护类型id不能为空!");
        Assert.notNull(maintenanceItemConfig.getName(), "项目名称不能为空!");
        return null;
    }
}