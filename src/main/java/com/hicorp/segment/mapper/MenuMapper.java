package com.hicorp.segment.mapper;

import com.hicorp.segment.mapper.basic.BasicMapper;
import com.hicorp.segment.pojo.Menu;

import java.util.List;


public interface MenuMapper extends BasicMapper<Menu> {

    @Override
    List<Menu> selectAll();

    List<Menu> selectByUserId(long userId);

    List<Long> selectAllId();
}