package com.bolo.auction.websocket.service.impl;

import com.bolo.auction.websocket.service.CompeteService;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 内存版实现
 * 主要用作性能标杆
 *
 * @author : LuoJingYan
 * Date    : 2021/4/29
 * Time    : 14:21
 */
@Service
public class MemoryCompeteServiceImpl implements CompeteService {
    private static final ConcurrentHashMap<String, AtomicLong> cache = new ConcurrentHashMap<>();

    {
        cache.put("1", new AtomicLong(1));
    }

    /**
     * 应价接口的无锁式实现方案。
     *
     * @param acocuntId 用户id
     * @param targetId  标的id
     * @param price     出价
     */
    @Override
    public boolean quote(String acocuntId, String targetId, Long price) {
        AtomicLong target = cache.get(acocuntId);
        long oldValue;
        do {
            oldValue = target.get();
            if (oldValue > price) {
                return false;
            }
        } while (!target.compareAndSet(oldValue, price));
        return true;
    }
}
