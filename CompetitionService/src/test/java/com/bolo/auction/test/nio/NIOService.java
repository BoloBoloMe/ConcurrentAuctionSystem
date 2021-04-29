package com.bolo.auction.test.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * 使用Java Nio编写的Socket 服务端
 */
public class NIOService {
    public static void main(String[] args) throws IOException {
        // 开启服务端Channel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 绑定监听端口
        serverSocketChannel.bind(new InetSocketAddress(6666));
        // 设置为非阻塞
        serverSocketChannel.configureBlocking(false);

        // 开启选择器
        Selector selector = Selector.open();
        // 服务端channel需要注册到选择器,关注的事件是：客户端发起连接
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            // 通过选择器检查是否有事件发生
            if (selector.select(1000) == 0) {
                System.out.println("没有事件发生");
                continue;
            }

            // 通过Selecter获取所有发生了事件的渠道
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> selKeyIterator = selectionKeys.iterator();
            // 遍历
            while (selKeyIterator.hasNext()) {
                SelectionKey selectionKey = selKeyIterator.next();
                // 如果渠道发生了OP_ACCEPT 事件,需要获取客户端连接事件
                if (selectionKey.isAcceptable()) {
                    // 获取发生了ACCEPT事件的服务器channel，并通过该渠道获取发起连接的客户端channel
                    ServerSocketChannel acceptablChannel = (ServerSocketChannel) selectionKey.channel();
                    SocketChannel clientShannel = acceptablChannel.accept();
                    System.out.println(String.format("有新的连接: %s", clientShannel.hashCode()));
                    // 将客户端channel注册到选择器,关注该渠道的 OP_READ 读事件,为该渠道绑定一个ByteBuffer
                    clientShannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }
                // 如果渠道发生了 OP_READ 事件，就从渠道中读取数据
                if (selectionKey.isReadable()) {
                    SocketChannel clientChannel = (SocketChannel) selectionKey.channel();
                    // 获取该渠道绑定的 ByteBuffer
                    ByteBuffer byteBuffer = (ByteBuffer) selectionKey.attachment();
                    // 从渠道中读取数据
                    clientChannel.read(byteBuffer);
                    System.out.println(String.format("渠道%s 传入数据：%s", clientChannel.hashCode(), new String(byteBuffer.array())));
                }

                // 处理完该SelectionKey之后，需要从Set集合中移除，避免被重复处理
                selKeyIterator.remove();
            }
        }

    }
}
