package com.hicorp.segment.exceptions.test;

import lombok.Getter;

/**
 * @Author: wqs
 * @Date: Created in 16:58 2021/5/10
 * @Description:
 * @ChineseDescription: 将不同的异常类，放入枚举类，方便使用。
 * @Modified_By:
 */
@Getter
public enum BusinessExceptionEnum {
    DATA_CHECK(DataCheckException.class),
    UN_LOGIN(UnLoginException.class),
    PERMISSION_FORBIDDEN(PermissionForbiddenException.class);

    private final Object object;

    BusinessExceptionEnum(Object object) {
        this.object = object;
    }
}
