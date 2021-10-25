package com.hicorp.segment.service.impl;

import com.hicorp.segment.pojo.PatrolTask;
import com.hicorp.segment.service.PatrolTaskService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service("patrolTaskService")
public class PatrolTaskServiceImpl extends BasicInterfaceImpl<PatrolTask> implements PatrolTaskService {
    @Override
    public Void dataValid(PatrolTask patrolTask) {
        Assert.notNull(patrolTask.getTaskName(), "任务名不能为空!");
        Assert.notNull(patrolTask.getTime(), "时间不能为空!");
        Assert.notNull(patrolTask.getUseTime(), "耗时不能为空!");
        Assert.notNull(patrolTask.getEmployee(), "员工不能为空!");
        Assert.notNull(patrolTask.getTaskContent(), "任务内容不能为空!");
        Assert.notNull(patrolTask.getGmtCreate(), "创建时间不能为空!");
        return null;
    }
}
