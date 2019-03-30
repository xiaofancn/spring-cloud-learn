import lombok.extern.slf4j.Slf4j;
import org.fansxnet.user.dto.User;
import org.fansxnet.web.client.UserClient;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * @Description: <br>
 * @Project: hades <br>
 * @CreateDate: Created in 2019/3/3 10:42 <br>
 * @Author: <a href="xiaofancn@qq.com">abc</a>
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = {WebApp.class})
@Slf4j
public class Abc {
    @Autowired
    UserClient userClient;

    @Test
    public void ab() {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("token-admin", "adasdfasd");
        User body = new User();
        HttpEntity<User> requestEntity = new HttpEntity<User>(body, requestHeaders);
        HttpHeaders headers = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();
        for (int i = 0; i < 1000; i++) {
            System.out.println(restTemplate.exchange("http://127.0.0.1:10010/api/web/app/createUser", HttpMethod.GET, requestEntity, String.class));
        }
    }

    @Test
    public void testMap() throws InterruptedException {
        Flux.just(1, 2, 3, 4)
                .log()
                .map(i -> {
                    return i * 2;
                })
                .subscribe(e -> log.info("get:{}", e));
    }
    @Test
    public void testFaltMap() throws InterruptedException {
        Flux.just(1,2,3,4)
                .log()
                .flatMap(e -> {
                    return Flux.just(e*2).delayElements(Duration.ofSeconds(1));
                })
                .subscribe(e -> log.info("get:{}",e));
        TimeUnit.SECONDS.sleep(10);
    }
}
