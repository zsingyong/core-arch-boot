package me.jet.zhu.core.common.netty.dto;

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
import lombok.Builder;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import org.jetbrains.io.SimpleChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

@Data
@Builder
@Accessors(chain = true)
public class IMSessionProxy {
    private static final Logger logger = LoggerFactory.getLogger(IMSessionProxy.class);
    private IMSession session;
    private Consumer<IMResponse> exCall;
    private Consumer<IMResponse> okCall;

    @SneakyThrows
    public synchronized void sendMessage() {
        final IMHeadI head = session.getRequest().getHead();
        head.setSt(System.currentTimeMillis());
        head.setTp(0);
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
                                logger.info("Client got a message：{}", msg);
                                final IMResponse response = JSONUtil.toBean(msg, IMResponse.class);
                                if (response.getData().isOk()) {
                                    if (okCall != null) {
                                        okCall.accept(response);
                                    }
                                } else {
                                    if (exCall != null) {
                                        exCall.accept(response);
                                    }
                                }
                                logger.info("Client close the channel!");
                                channelHandlerContext.close();
                            }

                            @Override
                            public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                                //todo 链接异常 重试建议
                                ctx.close();
                            }

                            @Override
                            public void channelActive(ChannelHandlerContext ctx) {
                                final String msg = JSONUtil.toJsonStr(session.getRequest());
                                logger.info("Client send a message：{}", msg);
                                ctx.writeAndFlush(msg);
                            }
                        });
            }
        });
        final ChannelFuture sync = bootstrap.connect(session.getHost1(), session.getPort1()).sync();
        sync.channel().closeFuture().sync();
        logger.info("客户端发送结束：{}", System.currentTimeMillis());

    }


}
