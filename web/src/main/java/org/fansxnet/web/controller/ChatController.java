package org.fansxnet.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.ReactiveRedisMessageListenerContainer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description: <br>
 * @Project: hades <br>
 * @CreateDate: Created in 2019/3/23 21:31 <br>
 * @Author: <a href="xiaofancn@qq.com">abc</a>
 */
@Slf4j
@Component
public class ChatController implements WebSocketHandler {
    final String topicName = "chat";
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    public static Map<String, WebSocketSession> sessionMap = new ConcurrentHashMap<>();

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        return session.receive()
                .log()
                .doOnSubscribe(webSocketMessage -> {
                    log.info("记录连接{}", session.getId());
                    sessionMap.put(session.getId(), session);
                    reactiveRedisTemplate.convertAndSend(topicName, session.getId() + "进来了。总人数为" + sessionMap.size()).toProcessor();
                })
                .flatMap(webSocketMessage -> {
                    String payTxt = session.getId()+":"+webSocketMessage.getPayloadAsText();
                    log.info("{}", payTxt);
                    return reactiveRedisTemplate.convertAndSend(topicName, payTxt).toProcessor();
                })
                .doOnComplete(() -> {
                            sessionMap.remove(session.getId());
                            String msg = session.getId() + "离开了。总人数为" + sessionMap.size();
                            reactiveRedisTemplate.convertAndSend(topicName, msg).toProcessor();
                            log.info(msg);
                        }
                )
                .then();

    }

    @Autowired
    ReactiveRedisTemplate<String, String> reactiveRedisTemplate;


    @PostConstruct
    public void init() {
        ReactiveRedisMessageListenerContainer container = new ReactiveRedisMessageListenerContainer(reactiveRedisTemplate.getConnectionFactory());
        container.receive(ChannelTopic.of(topicName))
                .log()
                .switchIfEmpty(Mono.error(new IllegalArgumentException()))
                .subscribe(stringStringMessage -> {
                    //一个线程里面的操作
                    Flux.fromIterable(sessionMap.values())
                            .map(webSocketSession ->
                                    webSocketSession.send(Mono.just(webSocketSession.textMessage(stringStringMessage.getMessage()))).toProcessor()
                            ).subscribe();
                });


    }
}
