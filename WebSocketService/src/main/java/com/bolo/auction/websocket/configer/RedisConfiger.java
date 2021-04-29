package com.bolo.auction.websocket.configer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;

/**
 * @author : LuoJingYan
 * Date    : 2021/4/29
 * Time    : 14:37
 */
@Configuration
public class RedisConfiger {

    @Bean
    public RedisScript<String> competeScript(){
        DefaultRedisScript<String> script = new DefaultRedisScript<>();
        script.setResultType(String.class);
//        script.setScriptSource();
        return script;
    }
}
