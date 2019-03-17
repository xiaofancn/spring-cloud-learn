package org.fansxnet.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.fansxnet.common.R;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.NettyWriteResponseFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @Description: <br>
 * @Project: hades <br>
 * @CreateDate: Created in 2019/3/16 08:40 <br>
 * @Author: <a href="xiaofancn@qq.com">abc</a>
 */
@Slf4j
@Configuration
public class GlobalFiltersConfig {

    @Component
    public class AuthFilter implements GlobalFilter, Ordered {
        @Override
        public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
            ServerHttpRequest request = exchange.getRequest();
            String token = request.getHeaders().getFirst("token");
            if (StringUtils.isEmpty(token)) {
                //验证失败，提示登录
                exchange.getResponse().getHeaders().add("Content-Type", "application/json;charset=UTF-8");
                return exchange.getResponse().writeAndFlushWith(Mono.just(Mono.just(exchange.getResponse().bufferFactory().wrap(JSON.toJSONBytes(R.just(-2, "请登录"))))));
            }
            log.info("header: \n{}\n", JSON.toJSONString(request.getHeaders(), SerializerFeature.PrettyFormat));
            //继续
            return chain.filter(exchange).
                    then(Mono.fromRunnable(() ->
                    {
                        log.info("third post filter");
                    }));
        }

        /**
         NettyWriteResponseFilter过滤器前面，
         */
        @Override
        public int getOrder() {
            return NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER-1;
        }
    }
}
