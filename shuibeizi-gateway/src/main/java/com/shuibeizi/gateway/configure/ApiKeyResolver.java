package com.shuibeizi.gateway.configure;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author: Administrator
 * @description:
 * @date: Create in 2018/10/27 0027 下午 3:10
 * @modified:
 */
public class ApiKeyResolver implements KeyResolver {

    public static final String BEAN_NAME = "apiKeyResolver";
    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        return Mono.just(exchange.getRequest().getPath().value());
    }
}
