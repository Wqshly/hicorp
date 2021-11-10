package com.hicorp.segment.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hicorp.segment.mapper.basic.BasicMapper;
import com.hicorp.segment.service.BasicInterface;
import com.hicorp.segment.utils.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.Assert;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;


/**
 * @Author:
 * @Date: Created in 0:11 2021/5/19
 * @Description:
 * @ChineseDescription:
 * @Modified_By:
 */
@Slf4j
public class BasicInterfaceImpl<T> implements BasicInterface<T> {

    // 因为泛型原因，不加 required = false 会导致 idea 报错。
    // 资源的注入最好使用 @Resources ，此处使用了通用 mapper ，只能这样使用。
    @Autowired(required = false)
    protected BasicMapper<T> basicMapper;

    protected Class<T> clazz;

    public BasicMapper<T> getBasicMapper() {
        return basicMapper;
    }

    public BasicInterfaceImpl() {
        //获取父的Type的数据
        Type type = this.getClass().getGenericSuperclass();
        //强转，才可以使用合适的方法
        ParameterizedType pType = (ParameterizedType) type;
        //获取父类的泛型类型
        this.clazz = (Class<T>) pType.getActualTypeArguments()[0];
    }

    @Override
    @Transactional(rollbackFor = Exception.class)   //事务回滚
    public ResultBean<Integer> createRecord(T data) {
        Assert.notNull(data, data.getClass().getName() + "传入数据不能为空!");
        this.dataValid(data);
        int callback;
        try {
            //抓是否新增    deviceInfoMapper.insert(deviceInfo)默认返还1
            callback = basicMapper.insert(data);
        } catch (Exception e) {
            log.error(e.getMessage());
            // 手动回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            if (e.getCause() instanceof PersistenceException) {
                return new ResultBean<>(-1, 400, "数据库连接失败!");
            }
            if (e.getCause() instanceof SQLIntegrityConstraintViolationException) {
                return new ResultBean<>(-1, 400, "添加失败，请保证必要数据不空且唯一值不重复!!");
            }
            return new ResultBean<>(400, "添加失败!");
        }
        return new ResultBean<>(callback);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultBean<Integer> createRecords(List<T> dataList) {
        Assert.notNull(dataList, "数据不能为空!");
        for (T data : dataList) {
            this.dataValid(data);
        }
        int callback;
        try {
            //抓是否新增    deviceInfoMapper.insert(deviceInfo)默认返还1
            callback = basicMapper.insertList(dataList);
            return new ResultBean<>(callback);
        } catch (Exception e) {
            log.error(e.getMessage());
            // 手动回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResultBean<>(-1, 400, "批量添加失败，请对应模板保证必要值非空且唯一值不重复!");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultBean<Integer> updateRecord(T data) {
        Assert.notNull(data, data.getClass().getName() + "传入数据不能为空!");
        this.dataValid(data);
        int callback;
        try {
            callback = basicMapper.updateByPrimaryKey(data);
        } catch (Exception e) {
            log.error(e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            if (e.getCause() instanceof PersistenceException) {
                return new ResultBean<>(-1, 400, "数据库连接失败!");
            }
            if (e.getCause() instanceof SQLIntegrityConstraintViolationException) {
                return new ResultBean<>(-1, 400, "修改失败，唯一值出现重复!");
            }
            return new ResultBean<>(400, "修改失败!");
        }
        return new ResultBean<>(callback);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultBean<Integer> updateNotNull(T data) {
        Assert.notNull(data, data.getClass().getName() + "传入数据不能为空!");
        this.dataValid(data);
        int callback;
        try {
            callback = basicMapper.updateByPrimaryKeySelective(data);
        } catch (Exception e) {
            log.error(e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            if (e.getCause() instanceof PersistenceException) {
                return new ResultBean<>(-1, 400, "数据库连接失败!");
            }
            if (e.getCause() instanceof SQLIntegrityConstraintViolationException) {
                return new ResultBean<>(-1, 400, "修改失败，唯一值出现重复!");
            }
            return new ResultBean<>(400, "修改失败!");
        }
        return new ResultBean<>(callback);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultBean<Integer> deleteRecord(Object value) {
        Assert.notNull(value, "数据不能为空!");
        int callback;
        try {
            callback = basicMapper.deleteByPrimaryKey(value);
        } catch (Exception e) {
            log.error(e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            if (e.getCause() instanceof PersistenceException) {
                return new ResultBean<>(-1, 400, "数据库连接失败!");
            }
            return new ResultBean<>(400, "删除失败!");
        }
        return new ResultBean<>(callback);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultBean<Integer> deleteRecords(String fieldName, List<Object> values) {
        int result;
        Assert.notNull(values, "数据不能为空!");
        // 声明Example
        Example example = new Example(this.clazz);
        // 获取条件对象
        Example.Criteria criteria = example.createCriteria();
        // 设置条件,第一个是pojo的属性名,第二个参数是条件
        criteria.andIn(fieldName, values);
        // 执行批量删除
        try {
            result = basicMapper.deleteByExample(example);
        } catch (Exception e) {
            log.error(e.getMessage());
            // 手动回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            if (e.getCause() instanceof PersistenceException) {
                return new ResultBean<>(-1, 400, "数据库连接失败!");
            }
            if (e.getCause() instanceof SQLIntegrityConstraintViolationException) {
                return new ResultBean<>(-1, 400, "该部门下还有成员，删除失败!");
            }
            return new ResultBean<>(400, "批量删除失败!");
        }
        return new ResultBean<>(result);
    }

    @Override
//    @Transactional(rollbackFor = Exception.class)
    public ResultBean<T> selectRecord(Object value) {
        Assert.notNull(value, "数据不能为空!");
        return new ResultBean<>(basicMapper.selectOneByExample(value));
    }

    @Override
//    @Transactional(rollbackFor = Exception.class)
    public ResultBean<List<T>> selectRecords(Object value) {
        Assert.notNull(value, value.getClass().getName() + "数据不能为空!");
        return new ResultBean<>(basicMapper.selectByExample(value));
    }

    @Override
    public ResultBean<PageInfo<T>> selectRecordsByPage(Integer page, Integer size, String sort, String fieldName, String value) {
        Assert.notNull(page, "页码不能为空!");
        Assert.notNull(size, "显示条数不能为空!");
        Assert.notNull(sort, "排序不能为空!");
        Assert.notNull(fieldName, "搜索字段不能为空!");
        Assert.notNull(value, "搜索内容不能为空!");
        // 声明Example
        Example example = new Example(this.clazz);
        // 获取条件对象
        Example.Criteria criteria = example.createCriteria();
        // 设置条件,第一个是pojo的属性名,第二个参数是条件
        criteria.andLike(fieldName, "%" + value + "%");

        return getPageInfoResultBean(page, size, sort, example);
    }

    private ResultBean<PageInfo<T>> getPageInfoResultBean(Integer page, Integer size, String sort, Example example) {
        try {
            PageHelper.startPage(page, size, sort.replace("->", " "));
            List<T> list = basicMapper.selectByExample(example);
            PageInfo<T> pageInfo = new PageInfo<>(list);
            return new ResultBean<>(pageInfo);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResultBean<>(400, "查询失败!");
        }
    }

    @Override
    public ResultBean<PageInfo<T>> selectRecordsByPage(Integer page, Integer size, String sort, String type, String fieldName, String value) {
        Assert.notNull(page, "页码不能为空!");
        Assert.notNull(size, "显示条数不能为空!");
        Assert.notNull(sort, "排序不能为空!");
        Assert.notNull(fieldName, "搜索字段不能为空!");
        Assert.notNull(value, "搜索内容不能为空!");
        // 声明Example
        Example example = new Example(this.clazz);
        // 获取条件对象
        Example.Criteria criteria = example.createCriteria();
        // 设置条件,第一个是pojo的属性名,第二个参数是条件
        criteria.andLike(fieldName, "%" + value + "%");
        criteria.andLike("type", "%" + type + "%");

        return getPageInfoResultBean(page, size, sort, example);
    }

    @Override
    public ResultBean<List<T>> queryInfo() {
        return new ResultBean<>(basicMapper.selectAll());
    }

    @Override
    public ResultBean<PageInfo<T>> queryInfo(Integer page, Integer size, String sort) {
        Assert.notNull(page, "页码不能为空!");
        Assert.notNull(size, "显示条数不能为空!");
        Assert.notNull(sort, "排序不能为空!");
        try {
            PageHelper.startPage(page, size, sort.replace("->", " "));
            List<T> list = basicMapper.selectAll();
            PageInfo<T> pageInfo = new PageInfo<>(list);
            return new ResultBean<>(pageInfo);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResultBean<>(400, "查询失败!");
        }
    }

    @Override
    public ResultBean<PageInfo<T>> queryInfo(Integer page, Integer size, String sort, String type) {
        Assert.notNull(page, "页码不能为空!");
        Assert.notNull(size, "显示条数不能为空!");
        Assert.notNull(sort, "排序不能为空!");
        // 声明Example
        Example example = new Example(this.clazz);
        // 获取条件对象
        Example.Criteria criteria = example.createCriteria();
        // 设置条件,第一个是pojo的属性名,第二个参数是条件
        criteria.andLike("type", "%" + type + "%");
        return getPageInfoResultBean(page, size, sort, example);
    }

    //拦截器 拦截非空
    @Override
    public Void dataValid(T data) {
        return null;
    }

}
