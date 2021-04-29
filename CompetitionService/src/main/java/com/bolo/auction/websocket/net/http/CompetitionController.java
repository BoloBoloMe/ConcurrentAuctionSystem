package com.bolo.auction.websocket.net.http;

import com.bolo.auction.websocket.service.CompeteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bolo.auction.websocket.service.impl.*;

/**
 * @author : LuoJingYan
 * Date    : 2021/4/29
 * Time    : 19:13
 */
@RestController
@RequestMapping("http/")
public class CompetitionController {
    @RequestMapping("{type}/quote")
    public RestResponse<Boolean> quote(@PathVariable("type") String type, String acocuntId, String targetId, Long price) {
        return new RestResponse<>(getService(type).quote(acocuntId, targetId, price));
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
