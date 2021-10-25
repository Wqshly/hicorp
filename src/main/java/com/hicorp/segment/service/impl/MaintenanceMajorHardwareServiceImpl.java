package com.hicorp.segment.service.impl;

import com.hicorp.segment.mapper.MaintenanceMajorHardwareMapper;
import com.hicorp.segment.pojo.MaintenanceMajorHardware;
import com.hicorp.segment.service.MaintenanceMajorHardwareService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;

@Slf4j
@Service("MaintenanceMajorHardwareService")
public class MaintenanceMajorHardwareServiceImpl extends BasicInterfaceImpl<MaintenanceMajorHardware> implements MaintenanceMajorHardwareService {
    @Resource
    private MaintenanceMajorHardwareMapper maintenanceMajorHardwareMapper;
    @Override
    public Void dataValid(MaintenanceMajorHardware maintenanceMajorHardware) {
        Assert.notNull(maintenanceMajorHardware.getName(), "设备名称不能为空!");
        Assert.notNull(maintenanceMajorHardware.getNumber(), "编号不能为空!");
        Assert.notNull(maintenanceMajorHardware.getSend(), "致不能为空!");
        Assert.notNull(maintenanceMajorHardware.getReason(), "原因及内容不能为空!");
        Assert.notNull(maintenanceMajorHardware.getEngineer(), "申请人（现场工程师）不能为空!");
        Assert.notNull(maintenanceMajorHardware.getDateDayEngineer(), "日期（工程师）不能为空!");
        Assert.notNull(maintenanceMajorHardware.getGmtCreate(), "创建时间不能为空!");
        return null;
    }
}
