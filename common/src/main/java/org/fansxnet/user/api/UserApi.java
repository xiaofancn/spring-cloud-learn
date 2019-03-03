package org.fansxnet.user.api;

import org.fansxnet.common.R;
import org.fansxnet.user.dto.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @Description: <br>
 * @Project: hades <br>
 * @CreateDate: Created in 2019/3/3 03:17 <br>
 * @Author: <a href="xiaofancn@qq.com">abc</a>
 */
@RequestMapping("/user")
public interface UserApi {

    @PostMapping(value = "/create",consumes = {"application/json"})
    User create(@RequestBody User user);

    @GetMapping("/ok")
    R ok();
}
