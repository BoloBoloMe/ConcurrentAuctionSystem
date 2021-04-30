package com.bolo.auction.websocket.net.websocket.support;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroupFuture;
import io.netty.channel.group.ChannelMatcher;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : LuoJingYan
 * Date    : 2021/4/30
 * Time    : 15:51
 */
public class ChannalGroupSupport {
    /**
     * 保存所有用户channel的Group
     * <p>
     * 可选的执行器：
     * * ImmediateEventExecutor：在提交任务的当前线程直接执行任务
     * * GlobalEventExecutor： 在一个单线程中异步执行提交的任务
     */
    private static final DefaultChannelGroup CHANNEL_GROUP = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 保存ChannelId 与 asShortText值
     */
    private static final ConcurrentHashMap<String, ChannelId> ID_HOLDER = new ConcurrentHashMap<>();


    public static void add(Channel channel) {
        ChannelId channelId = channel.id();
        ID_HOLDER.put(channelId.asShortText(), channelId);
        // 保证成功
        while (!CHANNEL_GROUP.add(channel)) ;
    }

    public static void remove(String shortId) {
        ChannelId channelId = ID_HOLDER.get(shortId);
        if (null != channelId) {
            if (CHANNEL_GROUP.remove(channelId)) {
                ID_HOLDER.remove(shortId);
            }
        }
    }

    public static void remove(ChannelId id) {
        remove(id.asShortText());
    }

    public static Channel find(String chortId) {
        ChannelId channelId = ID_HOLDER.get(chortId);
        if (null == channelId) return null;
        return CHANNEL_GROUP.find(channelId);
    }

    /**
     * 向所有渠道发送消息
     *
     * @param message
     * @return
     */
    public static ChannelGroupFuture write(Object message) {
        return CHANNEL_GROUP.write(message);
    }

    /**
     * 向指定渠道发送消息
     *
     * @param message
     * @return
     */
    public static ChannelGroupFuture write(Object message, ChannelMatcher matcher) {
        return CHANNEL_GROUP.write(message);
    }

    /**
     * 向所有渠道发送消息并刷新
     *
     * @param message
     * @return
     */
    public static ChannelGroupFuture writeAndFlush(Object message) {
        return CHANNEL_GROUP.writeAndFlush(message);
    }

    /**
     * 向特定渠道发送消息并刷新
     *
     * @param message
     * @return
     */
    public static ChannelGroupFuture writeAndFlush(Object message, ChannelMatcher matcher) {
        return CHANNEL_GROUP.writeAndFlush(message, matcher);
    }
}
