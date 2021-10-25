package com.hicorp.segment.exceptions.test;

import com.hicorp.segment.utils.ResultBean;

/**
 * @Author: wqs
 * @Date: Created in 22:41 2021/5/23
 * @Description:
 * @ChineseDescription:
 * @Modified_By:
 */
public interface GlobalExceptionStrategy {
    ResultBean<Void> errorHandle(Exception e);
}
