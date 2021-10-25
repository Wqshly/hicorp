package com.hicorp.segment.swagger;

import java.lang.annotation.*;

/**
 * @Author: wqs
 * @Date: Created in 21:50 2021/5/24
 * @Description:
 * @ChineseDescription:
 * @Modified_By:
 */

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiVersion {
    // 版本号(example: v1.0.1)
    String[] value() default {ApiVersionConstant.VERSION_1_0_0};
}
