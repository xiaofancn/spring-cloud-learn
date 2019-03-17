package org.fansxnet.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
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

    public class AuthFilter implements GlobalFilter {
        @Override
        public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
            ServerHttpRequest request = exchange.getRequest();
            String token = request.getHeaders().getFirst("token");
            if (StringUtils.isEmpty(token)) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
//                return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(BodyInserters.fromObject(R.error("please login"))).then();
            }
            log.info("header: \n{}\n", JSON.toJSONString(request.getHeaders(), SerializerFeature.PrettyFormat));
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                log.info("third post filter");
            }));
        }
    }

    @Order(0)
    @Bean
    AuthFilter authFilter() {
        return new AuthFilter();

    }
}
