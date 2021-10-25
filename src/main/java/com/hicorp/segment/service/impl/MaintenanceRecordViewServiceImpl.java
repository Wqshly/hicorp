package com.hicorp.segment.service.impl;

import com.hicorp.segment.pojo.MaintenanceRecordView;
import com.hicorp.segment.service.MaintenanceRecordViewService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service("MaintenanceRecordViewService")
public class MaintenanceRecordViewServiceImpl extends BasicInterfaceImpl<MaintenanceRecordView> implements MaintenanceRecordViewService {
    @Override
    public Void dataValid(MaintenanceRecordView maintenanceRecordView) {
        Assert.notNull(maintenanceRecordView.getClassificationId(), "维护类型id不能为空!");
        Assert.notNull(maintenanceRecordView.getDateDay(), "日期不能为空!");
        Assert.notNull(maintenanceRecordView.getNumber(), "编号不能为空!");
        return null;
    }
}