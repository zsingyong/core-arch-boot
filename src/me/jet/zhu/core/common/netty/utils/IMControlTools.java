package me.jet.zhu.core.common.netty.utils;

import cn.hutool.json.JSONUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.SneakyThrows;
import me.jet.zhu.core.common.netty.dto.IMRequest;
import me.jet.zhu.core.common.netty.dto.IMResponse;
import org.jetbrains.io.SimpleChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

public class IMControlTools {

    private static final Logger logger = LoggerFactory.getLogger(IMControlTools.class);

    @SneakyThrows
    public static void sendMessage(String host, Integer port, IMRequest request) {
        sendMessage(host, port, request, null);
    }

    @SneakyThrows
    public static void sendMessage(String host, Integer port, IMRequest imRequest, Consumer<IMResponse> callback) {
        final NioEventLoopGroup group = new NioEventLoopGroup();
        final Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) {
                socketChannel.pipeline()
                        .addLast(new StringEncoder())
                        .addLast(new StringDecoder())
                        .addLast(new SimpleChannelInboundHandlerAdapter<String>() {
                            @Override
                            protected void messageReceived(ChannelHandlerContext channelHandlerContext, String msg) throws Exception {
                                logger.info("客户端收到消息：{}", msg);
                                final IMResponse imResponse = JSONUtil.toBean(msg, IMResponse.class);
                                if (callback != null) {
                                    callback.accept(imResponse);
                                }
                                logger.info("客户端关闭通道");
                                channelHandlerContext.close();
                            }

                            @Override
                            public void channelActive(ChannelHandlerContext ctx) {
                                final String msg = JSONUtil.toJsonStr(imRequest);
                                logger.info("客户端发送消息：{}", msg);
                                ctx.writeAndFlush(msg);
                            }
                        });
            }
        });
        final ChannelFuture sync = bootstrap.connect(host, port).sync();
        sync.channel().closeFuture().sync();
        logger.info("客户端发送结束：{}", System.currentTimeMillis());
    }
}
