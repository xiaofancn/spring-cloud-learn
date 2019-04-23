package org.fansxnet.user.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.Map;

/**
 * @Description: <br>
 * @Project: hades <br>
 * @CreateDate: Created in 2019/4/5 09:36 <br>
 * @Author: <a href="xiaofancn@qq.com">abc</a>
 */
@Mapper
public interface CommonMapper {
    @Insert("Common")
    int insert(@Param("entity")Object record);

    @Update("Common")
    int updateByPrimaryKey(@Param("entity")Object record);

    @Update("Common")
    int update(@Param("entity")Object record,@Param("params") Map<String,Object> params);
}
