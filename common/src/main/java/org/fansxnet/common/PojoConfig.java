package org.fansxnet.common;

import lombok.Data;

import java.util.Map;

/**
 * @Description: <br>
 * @Project: hades <br>
 * @CreateDate: Created in 2019/4/5 10:41 <br>
 * @Author: <a href="xiaofancn@qq.com">abc</a>
 */
@Data
public class PojoConfig {
    private String table;
    private Map<String,String> propColumn;
    private String[] propKey;
}
