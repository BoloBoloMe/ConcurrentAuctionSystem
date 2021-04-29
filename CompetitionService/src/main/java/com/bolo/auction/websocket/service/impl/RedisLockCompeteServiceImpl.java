package com.bolo.auction.websocket.service.impl;

import com.bolo.auction.websocket.service.CompeteService;
import org.springframework.stereotype.Service;

/**
 * Redis分布式锁版实现
 *
 * @author : LuoJingYan
 * Date    : 2021/4/29
 * Time    : 20:04
 */
@Service
public class RedisLockCompeteServiceImpl implements CompeteService {
    @Override
    public boolean quote(String acocuntId, String targetId, Long price) {
        return false;
    }
}
