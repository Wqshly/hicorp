package com.hicorp.segment.service.impl;


import com.hicorp.segment.pojo.PatrolRoute;
import com.hicorp.segment.service.PatrolRouteService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service("patrolRouteService")
public class PatrolRouteServiceImpl extends BasicInterfaceImpl<PatrolRoute> implements PatrolRouteService {
    @Override
    public Void dataValid(PatrolRoute patrolRoute) {
        Assert.notNull(patrolRoute.getCycleNumber(), "巡检周期编号不能为空!");
        Assert.notNull(patrolRoute.getCycleName(), "巡检周期名称不能为空!");
        Assert.notNull(patrolRoute.getCycleOrder(), "次序不能为空!");
        Assert.notNull(patrolRoute.getPointName(), "巡检点名称不能为空!");
        Assert.notNull(patrolRoute.getGmtCreate(), "创建时间不能为空!");
        return null;
    }
}
