package me.jet.zhu.core.common.netty.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.intellij.openapi.components.Service;
import io.netty.channel.ChannelHandlerContext;
import me.jet.zhu.core.common.netty.dto.IMHeadO;
import me.jet.zhu.core.common.netty.dto.IMRequest;
import me.jet.zhu.core.common.netty.dto.IMResponse;
import me.jet.zhu.core.common.netty.dto.IMSession;
import org.jetbrains.io.SimpleChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.InetSocketAddress;

@Service
public class NettyDispatchHandler extends SimpleChannelInboundHandlerAdapter<String> {
    private static final Logger logger = LoggerFactory.getLogger(NettyDispatchHandler.class);


    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        final InetSocketAddress nativeAddress = (InetSocketAddress) ctx.channel().localAddress();
        final InetSocketAddress remoteAddress = (InetSocketAddress) ctx.channel().remoteAddress();
        logger.info("远程主机下线,本地主机：{},远程主机：{}", nativeAddress, remoteAddress);
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.info("执行异常：{}", cause);
        if (ctx != null) {
            ctx.close();
        }
    }


    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, String iMessage) throws Exception {
        logger.info("服务端1收到消息:{}", iMessage);
        final IMSession imSession = new IMSession();
        final IMResponse response = new IMResponse();
        final InetSocketAddress localAddress = (InetSocketAddress) channelHandlerContext.channel().localAddress();
        final InetAddress address = localAddress.getAddress();
        final String hostAddress = address.getHostAddress();
        final int port = localAddress.getPort();

        try {
            final IMRequest request = JSONUtil.toBean(iMessage, IMRequest.class);
            imSession.setRequest(request);
            imSession.setResponse(response);
            BeanUtil.copyProperties(request, response);
            final String oMessage = JSONUtil.toJsonStr(response);
            logger.info("服务端1返回消息:{}", oMessage);
            channelHandlerContext.writeAndFlush(oMessage);
        } catch (Exception ex) {
            final IMHeadO head = response.getHead();
            {

            }
        }
    }
}