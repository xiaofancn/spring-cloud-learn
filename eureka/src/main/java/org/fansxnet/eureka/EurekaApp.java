package org.fansxnet.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 *
 * @Description: <br>
 * @Project: hades <br>
 * @CreateDate: Created in 2019/3/3 02:26 <br>
 * @Author: <a href="xiaofancn@qq.com">abc</a>
 */
@EnableEurekaServer
@SpringBootApplication
public class EurekaApp {
    public static void main(String[] args) {
        SpringApplication.run(EurekaApp.class, args);
    }
}
