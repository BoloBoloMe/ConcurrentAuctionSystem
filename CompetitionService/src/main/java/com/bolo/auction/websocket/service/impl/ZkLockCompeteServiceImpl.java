package com.bolo.auction.websocket.service.impl;

import com.bolo.auction.websocket.service.CompeteService;
import org.springframework.stereotype.Service;

/**
 * Zookeeper 分布式锁版实现
 *
 * @author : LuoJingYan
 * Date    : 2021/4/29
 * Time    : 20:04
 */
@Service
public class ZkLockCompeteServiceImpl implements CompeteService {
    @Override
    public boolean quote(String acocuntId, Integer targetId, Long price) {
        return false;
    }
}
