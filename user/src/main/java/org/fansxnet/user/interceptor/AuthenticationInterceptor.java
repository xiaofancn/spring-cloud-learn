package org.fansxnet.user.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

/**
 * @Description: <br>
 * @Project: hades <br>
 * @CreateDate: Created in 2019/3/3 12:32 <br>
 * @Author: <a href="xiaofancn@qq.com">abc</a>
 */
@Slf4j
public class AuthenticationInterceptor implements HandlerInterceptor {
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Enumeration<String> names = request.getHeaderNames();
        while (names.hasMoreElements()){
            String k = names.nextElement();
            log.info("header[ {}:{} ]",k,request.getHeader(k));
        }
        return true;
    }
}
