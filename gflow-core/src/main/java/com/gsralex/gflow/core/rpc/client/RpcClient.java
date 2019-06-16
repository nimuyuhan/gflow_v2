package com.gsralex.gflow.core.rpc.client;

import io.netty.channel.EventLoopGroup;

import java.net.InetSocketAddress;

/**
 * @author gsralex
 * @version 2019/3/19
 */
public class RpcClient {

    private EventLoopGroup eventLoopGroup;
    private InetSocketAddress ip;


    public RpcClient(EventLoopGroup eventLoopGroup, InetSocketAddress ip) {
        this.eventLoopGroup = eventLoopGroup;
        this.ip = ip;
    }

    public InetSocketAddress getIp() {
        return ip;
    }

    public void close() {
        eventLoopGroup.shutdownGracefully();
    }
}
