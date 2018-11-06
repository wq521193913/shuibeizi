package com.shuibeizi.admin.startup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.shuibeizi.*.api")
@ComponentScan(basePackages = "com.shuibeizi",
        excludeFilters = {@ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.shuibeizi.*.api.")})
public class ShuiBeiZiAdminMain {

    public static void main(String[] args) {
        SpringApplication.run(ShuiBeiZiAdminMain.class, args);
    }

}
