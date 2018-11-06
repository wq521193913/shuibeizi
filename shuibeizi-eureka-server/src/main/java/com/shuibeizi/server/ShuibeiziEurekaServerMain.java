package com.shuibeizi.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class ShuibeiziEurekaServerMain {

	public static void main(String[] args) {
		SpringApplication.run(ShuibeiziEurekaServerMain.class, args);
	}
}
