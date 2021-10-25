package com.hicorp.segment.service.impl;

import com.hicorp.segment.mapper.MaintenanceEquipmentReplacementMapper;
import com.hicorp.segment.pojo.MaintenanceEquipmentReplacement;
import com.hicorp.segment.service.MaintenanceEquipmentReplacementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
@Slf4j
@Service("MaintenanceEquipmentReplacementService")
public class MaintenanceEquipmentReplacementServiceImpl extends BasicInterfaceImpl<MaintenanceEquipmentReplacement> implements MaintenanceEquipmentReplacementService {
    @Resource
    private MaintenanceEquipmentReplacementMapper maintenanceEquipmentReplacementMapper;
    @Override
    public Void dataValid(MaintenanceEquipmentReplacement maintenanceEquipmentReplacement) {
        Assert.notNull(maintenanceEquipmentReplacement.getName(), "设备名称不能为空!");
        Assert.notNull(maintenanceEquipmentReplacement.getNumber(), "编号不能为空!");
        Assert.notNull(maintenanceEquipmentReplacement.getSend(), "致不能为空!");
        Assert.notNull(maintenanceEquipmentReplacement.getSituationBeforeReplacement(), "更换前现场情况不能为空!");
        Assert.notNull(maintenanceEquipmentReplacement.getSituationAfterReplacement(), "更换后现场情况不能为空!");
        Assert.notNull(maintenanceEquipmentReplacement.getReplacementTime(), "更换时间不能为空!");
        Assert.notNull(maintenanceEquipmentReplacement.getEngineer(), "申请人（现场工程师）不能为空!");
        Assert.notNull(maintenanceEquipmentReplacement.getDateDayEngineer(), "日期（工程师）不能为空!");
        Assert.notNull(maintenanceEquipmentReplacement.getGmtCreate(), "创建时间不能为空!");
        return null;
    }
}
