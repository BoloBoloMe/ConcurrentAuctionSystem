package com.bolo.auction.business;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class BussinessSystemStarter {

    public static void main(String[] args) {
        log.info("业务系统启动中...");
        SpringApplication.run(BussinessSystemStarter.class, args);
    }
}
