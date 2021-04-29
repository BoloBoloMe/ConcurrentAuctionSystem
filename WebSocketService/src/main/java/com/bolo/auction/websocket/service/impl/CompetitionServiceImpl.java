package com.bolo.auction.websocket.service.impl;

import com.bolo.auction.websocket.service.CompetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * @author : LuoJingYan
 * Date    : 2021/4/29
 * Time    : 14:21
 */
@Service
public class CompetitionServiceImpl implements CompetitionService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 应价脚本
     */
    @Autowired
    private RedisScript<String> competeScript;

    /**
     * 应价接口的无锁式实现方案。
     * 本方案未采用分布式锁等技术保证并发的应价请求的串行执行，而是通过将关键的应价逻辑(倒计时是否归零 ->
     * 比较应价与当前最新价 -> 更新最新价 -> 更新中标用户) 封装成 redis 脚本，通过 redis 单线程执行命令的特性保证无锁的实现方式。
     * 相较采用分布式锁的实现方式，当前版本免去了复杂的争夺锁、释放锁等操作，IO次数降低到仅需要1次，所以资源消耗更低，速度更快。
     *
     * @param acocuntId 用户id
     * @param targetId  标的id
     * @param price     出价
     */
    @Override
    public void quote(String acocuntId, String targetId, Long price) {
        String result = redisTemplate.execute(competeScript, Collections.emptyList(), "");
    }
}
