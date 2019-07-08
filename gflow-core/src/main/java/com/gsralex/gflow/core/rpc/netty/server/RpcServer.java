package com.gsralex.gflow.core.rpc.netty.server;

import com.gsralex.gflow.core.rpc.netty.protocol.GenericDecoder;
import com.gsralex.gflow.core.rpc.netty.protocol.RpcReq;
import com.gsralex.gflow.core.rpc.netty.protocol.RpcResp;
import com.gsralex.gflow.core.rpc.netty.protocol.GenericEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * @author gsralex
 * @version 2019/3/15
 */
public class RpcServer {

    private static Logger LOG = LoggerFactory.getLogger(RpcServer.class);
    private HandlerCache handlerCache = new HandlerCache();
    private CountDownLatch latch = new CountDownLatch(1);

    public void registerHandler(Class clazz, Object impl) {
        handlerCache.registerHandler(clazz, impl);
    }

    public boolean start(int port)  {
        final boolean[] connected = {false};
        new Thread(new Runnable() {
            @Override
            public void run() {
                NioEventLoopGroup bossGroup = new NioEventLoopGroup();
                NioEventLoopGroup workGroup = new NioEventLoopGroup();
                ServerBootstrap bootstrap = new ServerBootstrap();
                bootstrap.group(bossGroup, workGroup)
                        .childHandler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            public void initChannel(SocketChannel channel) throws Exception {
                                channel.pipeline()
                                        //用定长处理粘包问题
                                        .addLast(new LengthFieldBasedFrameDecoder(65536, 0, 4, 0, 0))
//                                .addLast(new LineBasedFrameDecoder(1024))
//                                .addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()))
                                        .addLast(new GenericDecoder(RpcReq.class))
                                        .addLast(new GenericEncoder(RpcResp.class))
                                        .addLast(new RpcServerHandler(handlerCache));
                            }
                        })
                        .channel(NioServerSocketChannel.class)
                        .option(ChannelOption.SO_BACKLOG, 1024)
                        .childOption(ChannelOption.SO_KEEPALIVE, true);
                try {
                    ChannelFuture cf = bootstrap.bind(port).sync();
                    connected[0] = true;
                    latch.countDown();
                    cf.channel().closeFuture().sync();
                } catch (Exception e) {
                    LOG.error("RpcServer.start", e);
                    latch.countDown();
                } finally {
                    bossGroup.shutdownGracefully();
                    workGroup.shutdownGracefully();
                }
            }
        }).start();
//        latch.await(10000, TimeUnit.MILLISECONDS);
        return connected[0];
    }

}
