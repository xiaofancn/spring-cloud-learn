package org.fansxnet.user.api;

import org.fansxnet.common.R;
import org.fansxnet.user.dto.User;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @Description: <br>
 * @Project: hades <br>
 * @CreateDate: Created in 2019/3/3 03:17 <br>
 * @Author: <a href="xiaofancn@qq.com">abc</a>
 */
@RequestMapping("/user")
public interface UserApi {

    @PostMapping(value = "/create")
    User create(@RequestBody User user);

    @GetMapping(value = "/info")
    User info(@RequestParam("uid")  Long userId);

    @GetMapping(value = "/detail/{uid}")
    User detail(@PathVariable(name = "uid")Long userId);

    @GetMapping("/ok")
    R ok() throws Exception;
}
