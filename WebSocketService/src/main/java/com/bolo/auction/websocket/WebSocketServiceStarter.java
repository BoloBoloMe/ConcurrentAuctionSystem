package com.bolo.auction.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class WebSocketServiceStarter {
    public static void main(String[] args) {
        log.info("WebSocket服务端启动中...");
        SpringApplication.run(WebSocketServiceStarter.class, args);
    }
}
