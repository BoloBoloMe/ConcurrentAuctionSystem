package com.bolo.auction.websocket.service.impl;

import com.bolo.auction.websocket.service.CompeteService;
import org.springframework.stereotype.Service;

/**
 * Redis脚本版应价实现
 * 本方案未采用分布式锁等技术保证并发的应价请求的串行执行，而是通过将关键的应价逻辑(倒计时是否归零 ->
 * 比较应价与当前最新价 -> 更新最新价 -> 更新中标用户) 封装成 redis 脚本，通过 redis 单线程执行命令的特性保证无锁的实现方式。
 * 相较采用分布式锁的实现方式，当前版本免去了复杂的争夺锁、释放锁等操作，IO次数降低到仅需要1次，所以资源消耗更低，速度更快。
 *
 * @author : LuoJingYan
 * Date    : 2021/4/29
 * Time    : 20:04
 */
@Service
public class RedisScriptCompeteServiceImpl implements CompeteService {
    @Override
    public boolean quote(String acocuntId, String targetId, Long price) {
        return false;
    }
}
