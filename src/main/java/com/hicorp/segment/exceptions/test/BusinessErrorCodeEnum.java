package com.hicorp.segment.exceptions.test;

import lombok.Getter;

/**
 * @Author: wqs
 * @Date: Created in 16:52 2021/5/10
 * @Description:
 * @ChineseDescription: 包含业务逻辑错误代码，对应的 http 状态码，及提示信息。
 * @Modified_By:
 */
@Getter
public enum BusinessErrorCodeEnum {
    NOT_LOGIN(1001, 200, "User not login."),
    LOGIN_OUT(1002, 200, "User logged out!"),
    LOGIN_STATUS_TIMEOUT(1003, 200, "Login status timeout, maybe the session timed out!"),
    LOGIN_FAILED(1004, 200, "Login failed!"),
    NOT_EXIST_USER(1005, 200, "User does not exist!"),
    EXIST_USER(1006, 200, "This user is exist!"),
    CODE_WRONG(1007, 200, "Verify code wrong!"),
    DATABASE_CONNECT(3001, 200, "Database connect failed!"),
    DATA_QUERY(3002, 200, "Data query failed!"),
    DATA_CHANGE(3003, 200, "Data change failed!"),
    DATA_ROLLBACK(3004, 200, "Data rollback exception! check the rollback statement!"),
    DATA_NOT_FOUND(3005, 200, "Query data not found!"),
    PERMISSION_NOT_CHECK(5001, 401, "Some unknown conditions caused permission not checked."),
    INSUFFICIENT_PERMISSION(5002, 403, "Insufficient authority, method can not be executed!"),
    PERMISSION_CONFLICT(5003, 403, "An unknown error caused a permission conflict.");

    private final Integer code;

    private final Integer httpStatus;

    private final String msg;

    BusinessErrorCodeEnum(Integer code, Integer httpStatus, String msg) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.msg = msg;
    }

}
