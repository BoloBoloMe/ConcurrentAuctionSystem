package com.bolo.auction.websocket.net.websocket;

import com.bolo.auction.common.utils.BeanUtils;
import com.bolo.auction.websocket.net.websocket.support.ChannalGroupSupport;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author share
 * @date 2020/7/28 20:56
 */
@Slf4j
public class CompetotionHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        ChannalGroupSupport.add(ctx.channel());
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
        ChannalGroupSupport.remove(ctx.channel().id());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame msg) throws Exception {
        Channel channel = ctx.channel();
        log.info("Channel is {}", channel);
    }
}
