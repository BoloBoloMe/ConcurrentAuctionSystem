package com.bolo.auction.websocket.net.http;

import com.bolo.auction.websocket.service.CompeteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bolo.auction.websocket.service.impl.*;

import javax.annotation.PostConstruct;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceArray;

/**
 * @author : LuoJingYan
 * Date    : 2021/4/29
 * Time    : 19:13
 */
@RestController
@RequestMapping("http/")
public class CompetitionController {
    private final AtomicInteger index = new AtomicInteger(0);
    private final AtomicReferenceArray<Boolean> resultSet = new AtomicReferenceArray<>(1000);
    private final ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(1);

    @PostConstruct
    public void runScheduled() {
        scheduledThreadPool.scheduleAtFixedRate(() -> {
            int succeedCount = 0;
            for (int index = 0; index < resultSet.length(); index++) {
                if (resultSet.get(index)) {
                    succeedCount++;
                }
            }
            System.out.println(succeedCount / 200);
        }, 5, 5, TimeUnit.SECONDS);
    }


    @RequestMapping("{type}/quote")
    public RestResponse<Boolean> quote(@PathVariable("type") String type, String acocuntId, String targetId, Long price) {
        Boolean result = getService(type).quote(acocuntId, targetId, price);
        int indexInt = index.incrementAndGet();
        resultSet.compareAndSet(indexInt, false, result);
        return new RestResponse<>(result);
    }

    @Autowired
    private ApplicationContext applicationContext;

    private CompeteService getService(String type) {
        if ("mem".equals(type)) {
            return applicationContext.getBean(MemoryCompeteServiceImpl.class);
        } else if ("script".equals(type)) {
            return applicationContext.getBean(RedisScriptCompeteServiceImpl.class);
        } else if ("zklock".equals(type)) {
            return applicationContext.getBean(ZkLockCompeteServiceImpl.class);
        } else if ("redislock".equals(type)) {
            return applicationContext.getBean(RedisLockCompeteServiceImpl.class);
        } else {
            return new CompeteService() {
                @Override
                public boolean quote(String acocuntId, String targetId, Long price) {
                    return false;
                }
            };
        }
    }
}
