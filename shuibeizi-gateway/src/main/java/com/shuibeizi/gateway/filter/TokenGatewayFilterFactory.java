package com.shuibeizi.gateway.filter;

import com.shuibeizi.common.util.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

/**
 * @author: Administrator
 * @description:
 * @date: 2018/10/26 0026 21:31
 * @modified:
 */
@Component
public class TokenGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {

    private final Logger logger = LoggerFactory.getLogger(TokenGatewayFilterFactory.class);

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public GatewayFilter apply(Object config) {
        logger.info("================================1111111111111=================");
        return (exchange, chain) -> {
//            return chain.filter(exchange);
            String token = exchange.getRequest().getHeaders().getFirst("token");
            if (null != token && redisUtils.exists(token)) {
                return chain.filter(exchange);
            }
            //不合法
            ServerHttpResponse response = exchange.getResponse();
            //设置headers
            HttpHeaders httpHeaders = response.getHeaders();
            httpHeaders.add("Content-Type", "application/json; charset=UTF-8");
            httpHeaders.add("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");

            Result result = new Result(ResultCode.LOGIN_EXPIRE);
            DataBuffer bodyDataBuffer = response.bufferFactory().wrap(result.toString().getBytes());

            return response.writeWith(Mono.just(bodyDataBuffer));
        };
    }

}
