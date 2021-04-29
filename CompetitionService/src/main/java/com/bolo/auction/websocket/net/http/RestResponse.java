package com.bolo.auction.websocket.net.http;


import lombok.Data;

import java.io.Serializable;

/**
 * @author : LuoJingYan
 * Date    : 2021/4/29
 * Time    : 10:36
 */
@Data
public class RestResponse<D> implements Serializable {
    /**
     * 响应状态 0-成功
     */
    private String code;

    /**
     * 响应信息
     */
    private String message;

    /**
     * 响应数据
     */
    private D data;

    public RestResponse() {
    }

    public RestResponse(D data) {
        this.code = "0";
        this.message = "succeed";
        this.data = data;
    }

    public RestResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public RestResponse(String code, String message, D data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
