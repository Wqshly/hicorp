package com.hicorp.segment.service.impl;

import com.hicorp.segment.pojo.PatrolItem;
import com.hicorp.segment.service.PatrolItemService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service("patrolItemService")
public class PatrolItemServiceImpl extends BasicInterfaceImpl<PatrolItem> implements PatrolItemService {
    @Override
    public Void dataValid(PatrolItem patrolItem) {
        Assert.notNull(patrolItem.getPointNumber(), "巡检点编号不能为空!");
        Assert.notNull(patrolItem.getPointName(), "巡检点名称不能为空!");
        Assert.notNull(patrolItem.getName(), "项目名称不能为空!");
        Assert.notNull(patrolItem.getItemType(), "项目类型不能为空!");
        Assert.notNull(patrolItem.getGmtCreate(), "创建时间不能为空!");
        return null;
    }
}
