package com.gsralex.gflow.rpc.netty.client;


import com.gsralex.gflow.rpc.netty.protocol.RpcReq;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.util.UUID;

/**
 * @author gsralex
 * @version 2019/3/15
 */
public class ProxyHandler implements InvocationHandler {
    private Class clazz;
    private RpcClientManager rpcClientManager;
    private InetSocketAddress ip;
    private static final String METHOD_TOSTRING = "toString";

    public ProxyHandler(Class clazz, RpcClientManager rpcClientManager) {
        this.clazz = clazz;
        this.rpcClientManager = rpcClientManager;
    }

    public ProxyHandler(Class clazz, RpcClientManager rpcClientManager, InetSocketAddress ip) {
        this.clazz = clazz;
        this.rpcClientManager = rpcClientManager;
        this.ip = ip;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Exception {
        if (METHOD_TOSTRING.equals(method.getName())) {
            return proxy.toString();
        }
        RpcReq req = new RpcReq();
        String reqId = UUID.randomUUID().toString();
        req.setReqId(reqId);
        req.setParameters(args);
        req.setClassName(clazz.getName());
        req.setMethodName(method.getName());
        req.setParameters(args);
        RpcClientHandler rpcClientHandler;
        if (ip != null) {
            rpcClientHandler = rpcClientManager.getRpcClientHandler(ip);
        } else {
            rpcClientHandler = rpcClientManager.getRpcClientHandler();
        }
        RpcFuture future = rpcClientHandler.sendReq(req);
        return future.get();
    }
}
