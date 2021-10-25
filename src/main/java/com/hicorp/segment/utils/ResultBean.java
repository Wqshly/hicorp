package com.hicorp.segment.utils;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: wqs
 * @Date: Created in 11:28 2021/5/10
 * @Description: this class is a unified return and used generic.
 * @ChineseDescription: 本类使用泛型，是规范的统一返还类。
 * @Modified By:
 */
@Data
public class ResultBean<T> implements Serializable {
    public static final long serialVersionUID = 1L;

    private T data;
    private int code = SUCCESS;
    private String msg = "success";

    // 成功
    public static final int SUCCESS = 200;

    // 未知错误
    public static final int UNKNOWN_EXCEPTION = -99;

    // 用户状态异常
    public static final int NOT_LOGIN = 100;
    public static final int UNAUTHORIZED = 101;
    public static final int LOGIN_FAILED = 102;
    public static final int NOT_EXIST_USER = 103;
    public static final int EXIST_USER = 104;
    public static final int CODE_WRONG = 105;

    // 数据库异常
    public static final int SQL_CHANGE_WRONG = 301;
    public static final int SQL_QUERY_WRONG = 302;
    public static final int SQL_ROLLBACK_WRONG = 303;

    // 文件操作异常
    public static final int FILE_READ_WRONG = 501;
    public static final int FILE_WRITE_WRONG = 502;

    // 图片操作异常

    public ResultBean() {
        super();
    }

    public ResultBean(T data) {
        super();
        this.data = data;
    }

    public ResultBean(int code, String msg) {
        super();
        this.code = code;
        this.msg = msg;
    }

    public ResultBean(T data, int code, String msg) {
        super();
        this.data = data;
        this.code = code;
        this.msg = msg;
    }

    public ResultBean(Throwable e) {
        super();
        this.code = UNKNOWN_EXCEPTION;
        this.msg = e.toString();
    }
}
