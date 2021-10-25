//package com.wqs.haier.component;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.scheduling.annotation.EnableAsync;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//
//@Configuration
//@EnableScheduling   // 1.开启定时任务
//@EnableAsync        // 2.开启多线程
//public class Component {
//
//    @Async
//    @Scheduled(cron = "0/5 * * * * ?")  //间隔1秒
//    public void first() {
////        System.out.println("第一个定时任务开始 : " + LocalDateTime.now().toLocalTime() + "\r\n线程 : " + Thread.currentThread().getName());
//
//    }
//
//    @Async
//    @Scheduled(cron = "0/13 * * * * ?")
//    public void second() {
////        System.out.println("第二个定时任务开始 : " + LocalDateTime.now().toLocalTime() + "\r\n线程 : " + Thread.currentThread().getName());
////        System.out.println();
//    }
//}
