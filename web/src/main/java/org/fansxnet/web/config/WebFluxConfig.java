package org.fansxnet.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

/**
 * @Description: <br>
 * @Project: hades <br>
 * @CreateDate: Created in 2019/3/15 23:44 <br>
 * @Author: <a href="xiaofancn@qq.com">abc</a>
 */
@EnableWebFlux
@Configuration
public class WebFluxConfig implements WebFluxConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/doc/swagger-ui.html").addResourceLocations("classpath:/META-INF/");
        registry.addResourceHandler("/doc/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
