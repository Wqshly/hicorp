package com.hicorp.segment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;


// 使用通用 mapper 代替spring boot 的 MapperScan，否则在程序启动，第一次调用的时候，将出现 http status 500 error .
@SpringBootApplication
@MapperScan(basePackages = "com.hicorp.segment.mapper")
// 允许事务回滚
@EnableTransactionManagement
public class SegmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(SegmentApplication.class, args);
    }

}
