package com.bolo.auction.business.controller;

import com.bolo.auction.business.common.entity.RestResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 交易模块Controller
 *
 * @author : LuoJingYan
 * Date    : 2021/4/29
 * Time    : 10:42
 */
@RestController
@RequestMapping("deal/")
public class DealController {
    /**
     * 生成合同
     *
     * @param targetId 标的ID,Redis缓存的标的信息的key
     */
    @RequestMapping("contrac/generate/${targetId}")
    public RestResponse<Boolean> generateContract(@PathVariable("targetId") String targetId) {
        return new RestResponse<>(true);
    }
}
