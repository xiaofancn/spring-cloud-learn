//package org.fansxnet.gateway.config;
//
//import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
//import org.springframework.boot.web.servlet.ServletRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * @Description: <br>
// * @Project: hades <br>
// * @CreateDate: Created in 2019/3/3 19:03 <br>
// * @Author: <a href="xiaofancn@qq.com">abc</a>
// */
//@Configuration
//public class HystrixConfiguration {
//
//    @Bean(name = "hystrixRegistrationBean")
//    public ServletRegistrationBean servletRegistrationBean() {
//        ServletRegistrationBean registration = new ServletRegistrationBean(
//                new HystrixMetricsStreamServlet(), "/hystrix.stream");
//        registration.setName("hystrixServlet");
//        registration.setLoadOnStartup(1);
//        return registration;
//    }
//
//    @Bean(name = "hystrixForTurbineRegistrationBean")
//    public ServletRegistrationBean servletTurbineRegistrationBean() {
//        ServletRegistrationBean registration = new ServletRegistrationBean(
//                new HystrixMetricsStreamServlet(), "/actuator/hystrix.stream");
//        registration.setName("hystrixForTurbineServlet");
//        registration.setLoadOnStartup(1);
//        return registration;
//    }
//}