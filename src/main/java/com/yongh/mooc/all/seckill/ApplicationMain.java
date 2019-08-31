package com.yongh.mooc.all.seckill;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 描述: 根据慕课网 高并发秒杀API课程
 * app main
 *
 * @author soitis
 * @create 2019-08-28 15:59
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.yongh.mooc.all.seckill.mapper"})
@EnableCaching
public class ApplicationMain {
    public static void main(String args[]){
        SpringApplication.run (ApplicationMain.class,args);
        System.out.println ("应用软件主程序");
    }
}