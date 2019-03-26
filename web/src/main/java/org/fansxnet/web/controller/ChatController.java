package org.fansxnet.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description: <br>
 * @Project: hades <br>
 * @CreateDate: Created in 2019/3/23 21:31 <br>
 * @Author: <a href="xiaofancn@qq.com">abc</a>
 */
@Slf4j
public class ChatController implements WebSocketHandler {
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static Map<String, WebSocketSession> sessionMap = new ConcurrentHashMap<>();

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        sessionMap.put(session.getId(), session);
        log.info("记录连接{}", session.getId());
        return session.receive().doOnNext(webSocketMessage -> {
        }).concatMap(webSocketMessage -> {
            broadcast(webSocketMessage.getPayloadAsText());
            return Flux.empty();
        }).then();
    }

    private void broadcast(String text) {
        sessionMap.values().forEach(session -> {
            log.info("广播内容{}", text);
            session.send(Mono.just(session.textMessage(text))).toProcessor();
        });
    }
}
