package com.hicorp.segment.mapper.basic;

import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

// 加上该注解，否则启动将报错: Error creating bean with name 'basicMapper' defined .
@RegisterMapper
public interface BasicMapper<T> extends Mapper<T>, MySqlMapper<T> {
}