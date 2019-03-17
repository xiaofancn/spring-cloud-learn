package org.fansxnet.user;

import org.fansxnet.common.config.ExceptionHandlers;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import org.springframework.web.reactive.config.EnableWebFlux;

/**
 *
 * @Description: <br>
 * @Project: hades <br>
 * @CreateDate: Created in 2019/3/3 02:49 <br>
 * @Author: <a href="xiaofancn@qq.com">abc</a>
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableFeignClients
@EnableDiscoveryClient
@EnableWebFlux
@Import({ExceptionHandlers.class})
public class UserApp {
    public static void main(String[] args) {
        SpringApplication.run(UserApp.class, args);
    }

}
