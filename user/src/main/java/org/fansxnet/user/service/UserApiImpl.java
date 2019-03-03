package org.fansxnet.user.service;

import lombok.extern.slf4j.Slf4j;
import org.fansxnet.common.R;
import org.fansxnet.user.api.UserApi;
import org.fansxnet.user.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 *
 * @Description: <br>
 * @Project: hades <br>
 * @CreateDate: Created in 2019/3/3 03:20 <br>
 * @Author: <a href="xiaofancn@qq.com">abc</a>
 */
@Slf4j
@RestController
public class UserApiImpl implements UserApi {

    @Autowired
    private HttpServletRequest request;

    public User create(@RequestBody User user) {
        Enumeration<String> names = request.getHeaderNames();
        while (names.hasMoreElements()){
            String k = names.nextElement();
            log.info("header[ {}:{} ]",k,request.getHeader(k));
        }
        user.setUsername("使用为服务了" + user.getUsername());
        return user;
    }

    public R ok() {
        return R.ok("用戶爲服務");
    }
}
