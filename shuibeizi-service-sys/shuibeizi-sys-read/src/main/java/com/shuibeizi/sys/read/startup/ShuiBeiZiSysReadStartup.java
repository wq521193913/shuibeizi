package com.shuibeizi.sys.read.startup;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

/**
 * @author: Administrator
 * @description:
 * @date: 2018/10/31 0031 10:02
 * @modified:
 */
@SpringBootApplication
@EnableFeignClients
@ComponentScan(basePackages = "com.shuibeizi")
@MapperScan("com.shuibeizi.sys.dao")
@EnableEurekaClient
//@EnableHystrix
public class ShuiBeiZiSysReadStartup {
    public static void main(String[] args){
        SpringApplication.run(ShuiBeiZiSysReadStartup.class, args);
    }
}
