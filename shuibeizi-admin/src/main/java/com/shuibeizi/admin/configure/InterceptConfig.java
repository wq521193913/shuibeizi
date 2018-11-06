package com.shuibeizi.admin.configure;

import com.shuibeizi.admin.intercept.TokenInterceptor;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author: Administrator
 * @description: qibu
 * @date: 2018/3/22 0022 14:36
 * @modified:
 */
@Configuration
public class InterceptConfig extends WebMvcConfigurationSupport {

    @Bean
    public TokenInterceptor tokenInterceptor(){
        return new TokenInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/static/**","/login","/");
        super.addInterceptors(registry);
    }
}
