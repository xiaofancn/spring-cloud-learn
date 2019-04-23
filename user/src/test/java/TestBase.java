import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.RandomUtils;
import org.fansxnet.user.UserApp;
import org.fansxnet.user.entity.ProductArea;
import org.fansxnet.user.entity.ProductBrand;
import org.fansxnet.user.mapper.CommonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.util.Random;

/**
 * @Description: <br>
 * @Project: hades <br>
 * @CreateDate: Created in 2019/4/5 09:30 <br>
 * @Author: <a href="xiaofancn@qq.com">abc</a>
 */
@TestPropertySource(properties=
        {"spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration"})
@SpringBootTest(classes = UserApp.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class TestBase extends AbstractTestNGSpringContextTests {

    @Autowired
    CommonMapper commonMapper;

    //10000次执行，用10个线程处理
    @Test(threadPoolSize = 10,invocationCount=10000)
    public void abc(){
        ProductArea productArea = new ProductArea();
        productArea.setProductSpuId(RandomUtils.nextLong());
        ProductBrand brand = new ProductBrand();
        brand.setName("abc");
        commonMapper.insert(productArea);
        commonMapper.updateByPrimaryKey(productArea);
        commonMapper.insert(brand);
    }

}
