package com.hicorp.segment.service.impl;

import com.hicorp.segment.mapper.ApproveProcessMapper;
import com.hicorp.segment.mapper.DefaultApproverMapper;
import com.hicorp.segment.pojo.ApproveProcess;
import com.hicorp.segment.pojo.DefaultApprover;
import com.hicorp.segment.service.ApproveProcessService;
import com.hicorp.segment.utils.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author:
 * @Date: Created in 15:13 2021/5/25
 * @Description:
 * @ChineseDescription:
 * @Modified_By:
 */
@Service("approveProcessServiceImpl")
@Slf4j
public class ApproveProcessServiceImpl extends BasicInterfaceImpl<ApproveProcess> implements ApproveProcessService {

    @Resource
    private ApproveProcessMapper approveProcessMapper;

    @Resource
    private DefaultApproverMapper defaultApproverMapper;

    @Override
    public Void dataValid(ApproveProcess approveProcess) {
        Assert.notNull(approveProcess.getName(), "审批流名称不能为空!");
        Assert.notNull(approveProcess.getNumber(), "审批流编号不能为空!");
        Assert.notNull(approveProcess.getType(), "审批类别不能为空!");
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultBean<Integer> createRecord(ApproveProcess approveProcess) {
        this.dataValid(approveProcess);
        Date date = new Date();
        approveProcess.setCreateGmt(date);
        // 添加主表
        try {
            approveProcessMapper.insert(approveProcess);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        // 为子表插入返还的id
        for (DefaultApprover defaultApprover :
                approveProcess.getDefaultApproverList()) {
            defaultApprover.setApproveProcessId(approveProcess.getId());
        }
        // 添加子表
        try {
            defaultApproverMapper.insertList(approveProcess.getDefaultApproverList());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return new ResultBean<>();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultBean<Integer> updateNotNull(ApproveProcess approveProcess) {
        this.dataValid(approveProcess);
        Date date = new Date();
        approveProcess.setModifiedGmt(date);
        try {
            approveProcessMapper.updateByPrimaryKey(approveProcess);
//            defaultApproverMapper.updateByPrimaryKey(approveProcess.getDefaultApproverList());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return new ResultBean<>();
    }
}
