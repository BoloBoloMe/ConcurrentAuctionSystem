package com.bolo.auction.websocket.configer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

/**
 * @author : LuoJingYan
 * Date    : 2021/4/29
 * Time    : 14:37
 */
@Configuration
public class RedisConfiger {

    @Bean
    public RedisScript<String> competeScript() {
        DefaultRedisScript<String> defaultRedisScript = new DefaultRedisScript<>();
        defaultRedisScript.setResultType(String.class);
        defaultRedisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("pure_script.lua")));
        return defaultRedisScript;
    }
}
