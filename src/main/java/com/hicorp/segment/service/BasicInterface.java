package com.hicorp.segment.service;

import com.github.pagehelper.PageInfo;
import com.hicorp.segment.utils.ResultBean;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: wqs
 * @Date: Created in 23:48 2021/5/18
 * @Description:
 * @ChineseDescription:
 * @Modified_By:
 */
@Service
public interface BasicInterface<T> {

    // 新增单条数据
    ResultBean<Integer> createRecord(T data);

    // 批量新增
    ResultBean<Integer> createRecords(List<T> data);

    // 修改
    ResultBean<Integer> updateRecord(T data);

    // 批量修改
//    Void updateRecords(List<T> data);

    // 修改不为空
    ResultBean<Integer> updateNotNull(T data);

    // 批量修改不为空
//    Void updateNotNulls(List<T> data);

    // 删除单条数据
    ResultBean<Integer> deleteRecord(Object value);

    // 批量删除
    ResultBean<Integer> deleteRecords(String fieldName, List<Object> values);

    // 查询单条数据
    ResultBean<T> selectRecord(Object value);

    // 条件查询 List
    ResultBean<List<T>> selectRecords(Object value);

    // 条件查询(分页、排序)
    ResultBean<PageInfo<T>> selectRecordsByPage(Integer page, Integer size, String sort, String field, String value);

    // 查询所有
    ResultBean<List<T>> queryInfo();

    // 分页、排序
    ResultBean<PageInfo<T>> queryInfo(Integer page, Integer size, String sort);

    // 数据验证方法
    Void dataValid(T data);

}
