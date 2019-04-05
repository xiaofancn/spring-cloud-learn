package org.fansxnet.user.entity;

import lombok.Data;
import org.fansxnet.common.annotation.Model;

/**
 * @Description: <br>
 * @Project: hades <br>
 * @CreateDate: Created in 2019/4/5 09:34 <br>
 * @Author: <a href="xiaofancn@qq.com">abc</a>
 */
@Data
@Model(json = "ProductArea")
public class ProductArea {
    private Long id;
    private Long productSpuId;
}
