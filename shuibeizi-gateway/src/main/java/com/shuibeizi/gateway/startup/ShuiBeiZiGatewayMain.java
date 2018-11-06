package com.shuibeizi.gateway.startup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(value = {"com.shuibeizi"})
public class ShuiBeiZiGatewayMain {

    public static void main(String[] args) {
        SpringApplication.run(ShuiBeiZiGatewayMain.class, args);
    }
}
