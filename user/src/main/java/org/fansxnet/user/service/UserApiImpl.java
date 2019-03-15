package org.fansxnet.user.service;

import lombok.extern.slf4j.Slf4j;
import org.fansxnet.common.R;
import org.fansxnet.user.api.UserApi;
import org.fansxnet.user.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import reactor.core.publisher.Mono;

import java.util.Date;

/**
 * @Description: <br>
 * @Project: hades <br>
 * @CreateDate: Created in 2019/3/3 03:20 <br>
 * @Author: <a href="xiaofancn@qq.com">abc</a>
 */
@Slf4j
@RestController
public class UserApiImpl implements UserApi {


    @Override
    public User create(@RequestBody User user) {
        user.setUsername(user.getUsername() + "使用了用户服务");
        return user;
    }

    @Override
    public User info(Long userId) {
        User user = new User();
        user.setUserId(userId);
        user.setUsername("用户服务");
        user.setCreateTime(new Date());
        return user;
    }

    @Override
    public User detail(Long userId) {
        return info(userId);
    }




    public R ok() {
        HandlerMethodArgumentResolver a;
        return R.ok("用戶爲服務");
    }

    @RequestMapping("uu")
    public Mono<User> uu() {
        return Mono.just(info(99L));
    }

}
