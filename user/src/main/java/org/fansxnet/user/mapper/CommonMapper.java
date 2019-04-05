package org.fansxnet.user.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description: <br>
 * @Project: hades <br>
 * @CreateDate: Created in 2019/4/5 09:36 <br>
 * @Author: <a href="xiaofancn@qq.com">abc</a>
 */
@Mapper
public interface CommonMapper {
    @Insert("")
    public int insert(Object record);
}
