package com.gsralex.gflow.rpc.netty.client;

import com.gsralex.gflow.rpc.netty.protocol.GenericDecoder;
import com.gsralex.gflow.rpc.netty.protocol.GenericEncoder;
import com.gsralex.gflow.rpc.netty.protocol.RpcReq;
import com.gsralex.gflow.rpc.netty.protocol.RpcResp;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author gsralex
 * @version 2019/3/15
 */
public class RpcClientManager {
    private static final Logger LOG = LoggerFactory.getLogger(RpcClientManager.class);
    private List<RpcClientHandler> connectedClientHandles = new ArrayList<>();
    private Map<InetSocketAddress, RpcClient> connectedClients = new ConcurrentHashMap<>();


    private ThreadPoolExecutor pool = new ThreadPoolExecutor(2, 2, 300L, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(65536));
    private AtomicInteger seqRound = new AtomicInteger(0);

    private ReentrantLock lock = new ReentrantLock();
    private Condition connected = lock.newCondition();


    public RpcClientManager() {
    }

    public RpcClientManager(List<InetSocketAddress> ipList) {
        updateServerNodes(ipList);
    }


    public void updateServerNodes(List<InetSocketAddress> ipList) {
        //add nodes
        HashSet<InetSocketAddress> handlerSet = new HashSet<>();
        for (RpcClientHandler handler : connectedClientHandles) {
            handlerSet.add(handler.getRemoteIp());
        }
        for (InetSocketAddress ip : ipList) {
            if (!handlerSet.contains(ip)) {
                connect(ip);
            }
        }
        //remove nodes
        HashSet<InetSocketAddress> ipSet = new HashSet<>();
        for (InetSocketAddress ip : ipList) {
            ipSet.add(ip);
        }
        for (RpcClientHandler handler : connectedClientHandles) {
            if (!ipSet.contains(handler.getRemoteIp())) {
                handler.close();
                connectedClients.remove(handler.getRemoteIp());
                connectedClientHandles.remove(handler);
            }
        }
    }

    public void connect(InetSocketAddress remoteIp) {
        pool.submit(new Runnable() {
            @Override
            public void run() {
                EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
                Bootstrap b = new Bootstrap();
                b.group(eventLoopGroup).channel(NioSocketChannel.class);
                b.option(ChannelOption.TCP_NODELAY, true)
                        .handler(new ChannelInitializer<SocketChannel>() { // 绑定连接初始化器
                            @Override
                            public void initChannel(SocketChannel ch) throws Exception {
                                ch.pipeline()
                                        .addLast(new LengthFieldBasedFrameDecoder(65536, 0, 4, 0, 0))
//                                .addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()))
                                        .addLast(new GenericEncoder(RpcReq.class))
                                        .addLast(new GenericDecoder(RpcResp.class))
                                        .addLast(new RpcClientHandler(remoteIp));

                            }
                        });
                final ChannelFuture channelFuture = b.connect(remoteIp.getHostName(), remoteIp.getPort());
                channelFuture.addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture cf) {
                        if (cf.isSuccess()) {
                            //rpcclienthandler必须实例化
                            RpcClientHandler rpcClientHandler = channelFuture.channel().pipeline().get(RpcClientHandler.class);
                            RpcClient rpcClient = new RpcClient(eventLoopGroup, rpcClientHandler.getRemoteIp());
                            addHandler(rpcClientHandler, rpcClient);
                        } else {
                            LOG.error("RpcClient.connect ip:" + remoteIp, channelFuture.cause());
                        }
                    }
                });
            }
        });
    }


    private void addHandler(RpcClientHandler handler, RpcClient rpcClient) {
        connectedClientHandles.add(handler);
        connectedClients.put(rpcClient.getIp(), rpcClient);
        signalHandler();
    }

    private void signalHandler() {
        try {
            lock.lock();
            connected.signalAll();
        } finally {
            lock.unlock();
        }
    }

    private void waitForHandler(long time, TimeUnit unit) throws InterruptedException {
        try {
            lock.lock();
            connected.await(time, unit);
        } finally {
            lock.unlock();
        }
    }

    public RpcClientHandler findRpcClientHandler(InetSocketAddress ip) {
        for (int i = 0; i < connectedClientHandles.size(); i++) {
            if (connectedClientHandles.get(i).equals(ip)) {
                return connectedClientHandles.get(i);
            }
        }
        return null;
    }

    public void removeConnect(InetSocketAddress ip) {
        RpcClient client = connectedClients.remove(ip);
        client.close();
        connectedClientHandles.remove(findRpcClientHandler(ip));
    }

    public void removeAllConnect() {
        for (RpcClient client : connectedClients.values()) {
            client.close();
        }
        connectedClients.clear();
        connectedClientHandles.clear();
    }

    public List<InetSocketAddress> listConnectIp() {
        List<InetSocketAddress> ipList = new ArrayList<>();
        for (InetSocketAddress ip : connectedClients.keySet()) {
            ipList.add(ip);
        }
        return ipList;
    }

    public RpcClientHandler getRpcClientHandler(InetSocketAddress ip) {
        RpcClientHandler handler = findRpcClientHandler(ip);
        //如果没有当前clienthandler
        if (handler != null) {
            return handler;
        } else {
            connect(ip);
            int waitTimes = 3;
            int times = 0;
            while (times < waitTimes) {
                try {
                    handler = findRpcClientHandler(ip);
                    if (handler != null) {
                        return handler;
                    } else {
                        waitForHandler(10, TimeUnit.SECONDS);
                        times++;
                    }
                } catch (Exception e) {
                }
            }
        }
        return null;
    }

    public RpcClientHandler getRpcClientHandler() {
        while (true) {
            try {
                int size = connectedClientHandles.size();
                if (size == 0) {
                    waitForHandler(10, TimeUnit.SECONDS);
                    size = connectedClientHandles.size();
                }
                int round = seqRound.addAndGet(1) % size;
                return connectedClientHandles.get(round);
            } catch (Exception e) {
                LOG.error("RpcClientManager.getRpcClientHandler", e);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                }
            }
        }
    }

}
