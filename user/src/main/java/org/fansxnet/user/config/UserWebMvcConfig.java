package org.fansxnet.user.config;

import org.fansxnet.user.interceptor.AuthenticationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description: <br>
 * @Project: hades <br>
 * @CreateDate: Created in 2019/3/3 12:30 <br>
 * @Author: <a href="xiaofancn@qq.com">abc</a>
 */
@Configuration
public class UserWebMvcConfig implements WebMvcConfigurer {
    public void addInterceptors(InterceptorRegistry registry) {
        AuthenticationInterceptor authenticationInterceptor = new AuthenticationInterceptor();
        registry.addInterceptor(authenticationInterceptor).addPathPatterns("/**");
    }
}
