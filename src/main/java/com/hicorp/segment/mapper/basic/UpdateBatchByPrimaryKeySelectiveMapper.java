package com.hicorp.segment.mapper.basic;

import org.apache.ibatis.annotations.UpdateProvider;

import java.util.List;

@tk.mybatis.mapper.annotation.RegisterMapper
public interface UpdateBatchByPrimaryKeySelectiveMapper<T> {
    @UpdateProvider(type = BatchExampleProvider.class, method = "dynamicSQL")
    int updateBatchByPrimaryKeySelective(List<? extends T> recordList);
}
