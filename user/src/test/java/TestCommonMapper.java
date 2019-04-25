import org.apache.commons.lang3.RandomUtils;
import org.fansxnet.user.entity.ProductArea;
import org.fansxnet.user.entity.ProductBrand;
import org.fansxnet.user.mapper.CommonMapper;
import org.fansxnet.common.mybatis.provider.ExampleX;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

/**
 * @Description: <br>
 * @Project: hades <br>
 * @CreateDate: Created in 2019/4/5 09:30 <br>
 * @Author: <a href="xiaofancn@qq.com">abc</a>
 */
@Transactional
@Rollback
public class TestCommonMapper extends TestBase {


    @Autowired
    CommonMapper commonMapper;

    //10000次执行，用10个线程处理
    @Test//(threadPoolSize = 10,invocationCount=10000)
    public void testCommon() {
        ProductArea productArea = new ProductArea();
        productArea.setProductSpuId(RandomUtils.nextLong());
        ProductBrand brand = new ProductBrand();
        brand.setName("abc");
        commonMapper.xInsertSelective(productArea);
        commonMapper.xUpdateByPrimaryKeySelective(productArea);
        ExampleX exp = new ExampleX();
        exp.where("id<0");
        exp.where("id>#{id}", 0);
        productArea.setId(null);
        commonMapper.xUpdateByExampleSelective(productArea, exp);
    }

}
