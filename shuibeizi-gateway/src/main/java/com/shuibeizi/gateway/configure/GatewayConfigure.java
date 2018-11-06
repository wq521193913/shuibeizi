package com.shuibeizi.gateway.configure;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: Administrator
 * @description:
 * @date: Create in 2018/10/27 0027 下午 5:50
 * @modified:
 */
@Configuration
public class GatewayConfigure {

    @Bean(ApiKeyResolver.BEAN_NAME)
    public KeyResolver apiKeyResolver(){
        return new ApiKeyResolver();
    }

//    @Bean
//    public TokenGatewayFilterFactory tokenGatewayFilterFactory(){
//        return new TokenGatewayFilterFactory();
//    }
}
