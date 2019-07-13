package com.gsralex.gflow.rpc.netty.server;

import com.gsralex.gflow.rpc.netty.protocol.RpcReq;
import com.gsralex.gflow.rpc.netty.protocol.RpcResp;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author gsralex
 * @version 2019/3/15
 */
public class RpcServerHandler extends SimpleChannelInboundHandler<RpcReq> {

    private static final Logger LOG = LoggerFactory.getLogger(RpcServerHandler.class);
    private HandlerCache handlerCache;

    public RpcServerHandler(HandlerCache handlerCache) {
        this.handlerCache = handlerCache;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcReq req) {
        RpcResp resp = new RpcResp();
        resp.setReqId(req.getReqId());
        Object result = null;
        try {
            result = doAction(req);
        } catch (Exception e) {
            LOG.error("RpcServerHandler.channelRead0 reqId:" + req.getReqId(), e);
            resp.setMsg(e.getMessage());
        }
        resp.setData(result);
        ctx.writeAndFlush(resp);
    }


    public Object doAction(RpcReq rpcReq) throws Exception {
        String className = rpcReq.getClassName();
        if (handlerCache.containsHandler(className)) {
            Object instance = handlerCache.getHandler(className);
            if (instance == null) {
                //TODO:throw exception
            }
            Method method = instance.getClass().getMethod(rpcReq.getMethodName(), getParameterClass(rpcReq.getParameters()));
            return method.invoke(instance, rpcReq.getParameters());
        }
        return null;
    }


    public Class<?>[] getParameterClass(Object[] parameters) {
        Class<?>[] classArr = new Class[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            classArr[i] = parameters[i].getClass();
        }
        return classArr;
    }


    public void registerHandler(Class<?> clazz, Object impl) {
        handlerCache.registerHandler(clazz, impl);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        LOG.error("RpcServerHandler.exceptionCaught", cause);
        ctx.close();
    }

    public static class D {
        public void d(long d1, String d2, long d3) {
            System.out.println("d");
        }

        public void d1(long d1, String d2, long d3) {
            System.out.println("d");
        }

        public void d2(long d1, String d2, long d3) {
            System.out.println("d");
        }

        public void d3(long d1, String d2, long d3) {
            System.out.println("d");
        }

        public void d4(long d1, String d2, long d3) {
            System.out.println("d");
        }

        public void d5(long d1, String d2, long d3) {
            System.out.println("d");
        }

        public void d6(long d1, String d2, long d3) {
            System.out.println("d");
        }

        public void d7(long d1, String d2, long d3) {
            System.out.println("d");
        }

        public void d8(long d1, String d2, long d3) {
            System.out.println("d");
        }

        public void d9(long d1, String d2, long d3) {
            System.out.println("d");
        }

        public void d10(long d1, String d2, long d3) {
            System.out.println("d");
        }

        public void d11(long d1, String d2, long d3) {
            System.out.println("d");
        }

        public void d12(long d1, String d2, long d3) {
            System.out.println("d");
        }

        public void d13(long d1, String d2, long d3) {
            System.out.println("d");
        }

        public void d14(long d1, String d2, long d3) {
            System.out.println("d");
        }


        public void d(long d1, String d2, long d3, long d4) {
            System.out.println("d");
        }

        public void d15(long[] d1) {
        }

    }

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        System.out.println(long.class);
        System.out.println(Long.class);

        HandlerCache handlerCache = new HandlerCache();
        RpcServerHandler handler = new RpcServerHandler(handlerCache);
        handler.registerHandler(D.class, new D());
        D d = new D();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            String className = D.class.getName();
            String methodName = "d15";
            Object[] parameters = new Object[]{new Long[]{1L}};
            Method method = d.getClass().getMethod(methodName, handler.getParameterClass(parameters));
//            handler.findMethod(className, methodName, parameters);
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
