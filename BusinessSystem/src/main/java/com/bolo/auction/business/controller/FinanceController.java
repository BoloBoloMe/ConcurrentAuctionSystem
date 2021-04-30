package com.bolo.auction.business.controller;

import com.bolo.auction.common.entity.RestResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 财务模块Controller
 *
 * @author : LuoJingYan
 * Date    : 2021/4/29
 * Time    : 10:30
 */
@RestController
@RequestMapping("finance/")
public class FinanceController {
    /**
     * 冻结应价接口
     *
     * @param acocuntId 账户id
     * @param price     报价
     * @return
     */
    @RequestMapping("quote/free")
    public RestResponse<Boolean> freezeQuotePrice(@RequestParam String acocuntId, @RequestParam Long price) {
        return new RestResponse<>(true);
    }
}
