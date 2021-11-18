package com.hicorp.segment.mapper;

import com.hicorp.segment.mapper.basic.BasicMapper;
import com.hicorp.segment.pojo.ApproveProcess;
import com.hicorp.segment.pojo.DefaultApprover;

import java.util.List;

/**
 * ApproveProcessMapper继承基类
 */
public interface ApproveProcessMapper extends BasicMapper<ApproveProcess> {
    @Override
    List<ApproveProcess> selectAll();

    List<DefaultApprover> findDefaultApprover(Long id);
}