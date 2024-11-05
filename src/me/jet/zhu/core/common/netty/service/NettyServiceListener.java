package me.jet.zhu.core.common.netty.service;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class NettyServiceListener {

    private Logger logger = LoggerFactory.getLogger(NettyServiceListener.class);
    private EventLoopGroup bossGroup = new NioEventLoopGroup();
    private EventLoopGroup workGroup = new NioEventLoopGroup();
    private Boolean isStarted = false;

    public synchronized void start() {
        if (isStarted) {
            return;
        }
        synchronized (isStarted) {
            isStarted = true;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    logger.info("启动服务：{}", System.currentTimeMillis());
                    ServerBootstrap serverBootstrap = new ServerBootstrap();
                    serverBootstrap.group(bossGroup, workGroup).channel(NioServerSocketChannel.class)
                            .childHandler(new ChannelInitializer<SocketChannel>() {
                                @Override
                                protected void initChannel(SocketChannel socketChannel) {
                                    socketChannel.pipeline()
                                            .addLast(new StringDecoder())
                                            .addLast(new StringEncoder())
                                            .addLast(new NettyDispatchHandler());
                                }
                            }).option(ChannelOption.SO_BACKLOG, 128).childOption(ChannelOption.SO_KEEPALIVE, true);
                    final ChannelFuture future = serverBootstrap.bind(6843).sync();
                    future.channel().closeFuture().sync();
                } catch (InterruptedException e) {
                    logger.info("启动服务异常：{}", e);
                    throw new RuntimeException("执行异常", e);
                } finally {
                    stop();
                }
            }
        }).start();

    }

    public synchronized void stop() {
        logger.info("关闭服务{}", System.currentTimeMillis());
        bossGroup.shutdownGracefully();
        workGroup.shutdownGracefully();
    }
}
