package com.gsralex.gflow.rpc.netty.client;

import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;

/**
 * @author gsralex
 * @version 2019/3/15
 */
public class RpcClientFactory {

    public static <T> T create(Class<T> clazz, RpcClientManager clientManager) {
        Object instance = Proxy.newProxyInstance(RpcClientFactory.class.getClassLoader(), new Class[]{
                clazz
        }, new ProxyHandler(clazz, clientManager));
        return (T) instance;
    }

    public static <T> T create(Class<T> clazz, RpcClientManager clientManager, InetSocketAddress ip) {
        Object instance = Proxy.newProxyInstance(RpcClientFactory.class.getClassLoader(), new Class[]{
                clazz
        }, new ProxyHandler(clazz, clientManager, ip));
        return (T) instance;
    }
}
