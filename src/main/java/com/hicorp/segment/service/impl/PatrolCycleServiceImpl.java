package com.hicorp.segment.service.impl;

import com.hicorp.segment.pojo.PatrolCycle;
import com.hicorp.segment.service.PatrolCycleService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
@Service("patrolCycleService")
public class PatrolCycleServiceImpl extends BasicInterfaceImpl<PatrolCycle> implements PatrolCycleService {
    @Override
    public Void dataValid(PatrolCycle patrolCycle) {
        Assert.notNull(patrolCycle.getCycleNumber(), "巡检周期编号不能为空!");
        Assert.notNull(patrolCycle.getCycleName(), "巡检周期名称不能为空!");
        Assert.notNull(patrolCycle.getFrequency(), "次数不能为空!");
        Assert.notNull(patrolCycle.getCycleInterval(), "间隔不能为空!");
        Assert.notNull(patrolCycle.getUseTime(), "耗时不能为空!");
        Assert.notNull(patrolCycle.getGmtCreate(), "创建时间不能为空!");
        return null;
    }
}
