package org.fansxnet.user.entity;

import lombok.Data;
import org.fansxnet.common.mybatis.annotation.Model;

/**
 * @Description: <br>
 * @Project: hades <br>
 * @CreateDate: Created in 2019/4/5 09:34 <br>
 * @Author: <a href="xiaofancn@qq.com">abc</a>
 */
@Data
@Model(json = "ProductBrand")
public class ProductBrand {
    private Long id;
    private String name;

}
