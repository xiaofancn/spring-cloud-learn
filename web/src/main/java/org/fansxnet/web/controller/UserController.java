package org.fansxnet.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.fansxnet.common.R;
import org.fansxnet.user.dto.User;
import org.fansxnet.web.client.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Enumeration;

/**
 *
 * @Description: <br>
 * @Project: hades <br>
 * @CreateDate: Created in 2019/3/3 03:26 <br>
 * @Author: <a href="xiaofancn@qq.com">abc</a>
 */
@Slf4j
@RestController
@RequestMapping("/app")
public class UserController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private UserClient userClient;

    @GetMapping("/createUser")
    public R create() {
        Enumeration<String> names = request.getHeaderNames();
        while (names.hasMoreElements()){
            String k = names.nextElement();
            log.info("header[ {}:{} ]",k,request.getHeader(k));
        }
        return R.ok(userClient.create(createa()));
    }

    @GetMapping("/ok")
    public R ok() {
        return  R.ok(createa());
    }

    private User createa(){
        User user = new User();
        user.setUsername("web");
        user.setPassword("123456");
        user.setCreateTime(new Date());
        System.out.println("web"+user.toString());
        return user;
    }

}
