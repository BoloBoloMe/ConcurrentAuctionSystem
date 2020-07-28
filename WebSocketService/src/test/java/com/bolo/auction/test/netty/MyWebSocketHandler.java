package com.bolo.auction.test.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import lombok.extern.slf4j.Slf4j;

/**
 * @author share
 * @date 2020/7/28 20:56
 */
@Slf4j
public class MyWebSocketHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

    public MyWebSocketHandler() {
        log.info("MyWebSocketHandler is construction:{}", toString());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame msg) throws Exception {
        Channel channel = ctx.channel();
        log.info("Channel is {}", channel);
    }
}
