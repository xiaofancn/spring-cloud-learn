import org.fansxnet.user.UserApp;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description: <br>
 * @Project: hades <br>
 * @CreateDate: Created in 2019/4/5 09:30 <br>
 * @Author: <a href="xiaofancn@qq.com">abc</a>
 */
@TestPropertySource(properties =
        {"spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration"})
@SpringBootTest(classes = UserApp.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class TestBase extends AbstractTestNGSpringContextTests {

}
