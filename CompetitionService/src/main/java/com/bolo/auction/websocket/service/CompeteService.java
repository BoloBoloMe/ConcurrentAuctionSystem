package com.bolo.auction.websocket.service;

/**
 * 竞价服务
 *
 * @author : LuoJingYan
 * Date    : 2021/4/29
 * Time    : 11:18
 */
public interface CompeteService {
    /**
     * 应价接口
     *
     * 大体流程：倒计时是否归零 -> 比较应价与当前最新价 -> 更新最新价 -> 更新中标用户
     * @param acocuntId 用户id
     * @param targetId 标的id
     * @param price 出价
     */
    boolean quote(String acocuntId, Integer targetId, Long price);
}
