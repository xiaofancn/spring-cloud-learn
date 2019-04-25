package org.fansxnet.user.mapper;

import org.apache.ibatis.annotations.*;
import org.fansxnet.common.mybatis.provider.ExampleX;
import org.fansxnet.common.mybatis.provider.SimpleSqlProvider;

/**
 * @Description: <br>
 * @Project: hades <br>
 * @CreateDate: Created in 2019/4/5 09:36 <br>
 * @Author: <a href="xiaofancn@qq.com">abc</a>
 */
@Mapper
public interface CommonMapper {
    @InsertProvider(type = SimpleSqlProvider.class, method = "insertSelective")
    @Options(useGeneratedKeys = true, keyProperty = "entity.id")
    int xInsertSelective(@Param("entity")Object record);

    @UpdateProvider(type = SimpleSqlProvider.class, method = "updateByPrimaryKeySelective")
    int xUpdateByPrimaryKeySelective(@Param("entity")Object record);

    @UpdateProvider(type = SimpleSqlProvider.class, method = "updateByExampleSelective")
    int xUpdateByExampleSelective(@Param("entity")Object record,@Param("example") ExampleX exampleX);
}
