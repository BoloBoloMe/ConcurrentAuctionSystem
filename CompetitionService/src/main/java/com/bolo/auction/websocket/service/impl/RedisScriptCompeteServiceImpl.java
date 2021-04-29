package com.bolo.auction.websocket.service.impl;

import com.bolo.auction.websocket.service.CompeteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.concurrent.atomic.AtomicLongArray;

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
    // 本机出过的最新价,数组下标即场次号编号
    private static final AtomicLongArray array = new AtomicLongArray(1000);
    @Autowired
    private RedisScript<String> script;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    @Override
    public boolean quote(String acocuntId, Integer targetId, Long price) {
        // 跟本进程内缓存的最高出价进行比较，如果高于该价格才有继续执行的资格
        long lastPrice = array.get(targetId);
        if (lastPrice > price) {
            return false;
        }
        array.compareAndSet(targetId, lastPrice, price);
        // todo：异步发送MQ,应价记录入库
        // todo：冻结用户金额
        String result = redisTemplate.execute(script, Collections.singletonList("compete"), targetId.toString(), "countdown", "last_price", price.toString());
        return "succeed".equals(result);
    }
}
