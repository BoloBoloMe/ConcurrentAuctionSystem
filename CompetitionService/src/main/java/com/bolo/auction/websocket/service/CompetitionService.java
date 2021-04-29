package com.bolo.auction.websocket.service;

/**
 * 竞价服务
 *
 * @author : LuoJingYan
 * Date    : 2021/4/29
 * Time    : 11:18
 */
public interface CompetitionService {
    /**
     * 应价接口
     *
     * 大体流程：出价低于当前价 -> 冻结用户金额 -> 更新最新价
     * @param acocuntId 用户id
     * @param targetId 标的id
     * @param price 出价
     */
    void quote(String acocuntId, String targetId, Long price);
}
