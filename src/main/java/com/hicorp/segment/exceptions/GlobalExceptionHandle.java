package com.hicorp.segment.exceptions;

import com.hicorp.segment.utils.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: wqs
 * @Date: Created in 21:42 2021/5/23
 * @Description:
 * @ChineseDescription: Controller层的统一异常处理。
 * @Modified_By:
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandle {

    @ExceptionHandler(Exception.class)
    public ResultBean<Void> errorHandle(Exception e) {
        if (e instanceof BindException) {
            BindingResult bindingResult = ((BindException) e).getBindingResult();
            List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
            Map<String, String> errorMsg = new HashMap<>();
            for (FieldError error :
                    fieldErrorList) {
                errorMsg.put(error.getField(), error.getDefaultMessage());
            }
            log.error(errorMsg.toString());
            return new ResultBean<>(400, errorMsg.toString());
        } else if (e instanceof SQLIntegrityConstraintViolationException || e instanceof DuplicateKeyException) {
//            log.error(e.getMessage());
            return new ResultBean<>(400, "添加的项在数据库中已存在~" + e.getMessage());
        } else if (e instanceof ClassCastException) {
            return new ResultBean<>(400, "数据类型不匹配: " + e.getMessage());
        }
        return new ResultBean<>(400, "others error: " + e.getMessage());
    }
}
