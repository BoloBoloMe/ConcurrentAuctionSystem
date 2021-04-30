package com.bolo.auction.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class CompetitionServiceStarter {
    public static void main(String[] args) {
        log.info("CompetitionService服务端启动中...");
        SpringApplication.run(CompetitionServiceStarter.class, args);
    }
}
