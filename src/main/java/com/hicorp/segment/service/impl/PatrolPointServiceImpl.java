package com.hicorp.segment.service.impl;

import com.hicorp.segment.pojo.PatrolPoint;
import com.hicorp.segment.service.PatrolPointService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service("patrolPointService")
public class PatrolPointServiceImpl extends BasicInterfaceImpl<PatrolPoint> implements PatrolPointService {
    @Override
    public Void dataValid(PatrolPoint patrolPoint) {
        Assert.notNull(patrolPoint.getNumber(), "巡检点编号不能为空!");
        Assert.notNull(patrolPoint.getName(), "巡检点名称不能为空!");
        Assert.notNull(patrolPoint.getElectronicLabelId(), "电子标签ID不能为空!");
        Assert.notNull(patrolPoint.getCardPrintingNumber(), "卡印刷编号不能为空!");
        Assert.notNull(patrolPoint.getGmtCreate(), "创建时间不能为空!");
        return null;
    }
}
