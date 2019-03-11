package org.fansxnet.user.dto;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 *
 * @Description: <br>
 * @Project: hades <br>
 * @CreateDate: Created in 2019/3/3 03:17 <br>
 * @Author: <a href="xiaofancn@qq.com">abc</a>
 */
@ToString
@Data
public class User {
    private Long userId;
    private String username;
    private String password;
    private Date createTime;
    private String detail;
}
