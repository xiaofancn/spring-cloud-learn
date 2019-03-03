package org.fansxnet.web.client;

import org.fansxnet.user.api.UserApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 *
 * @Description: <br>
 * @Project: hades <br>
 * @CreateDate: Created in 2019/3/3 03:23 <br>
 * @Author: <a href="xiaofancn@qq.com">abc</a>
 */
@FeignClient(value = "user-service")
public interface UserClient extends UserApi {
}
