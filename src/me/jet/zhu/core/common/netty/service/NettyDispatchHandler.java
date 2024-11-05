package me.jet.zhu.core.common.netty.service;

import cn.hutool.json.JSONUtil;
import com.intellij.openapi.components.Service;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import me.jet.zhu.core.common.netty.cache.IControlQueueCache;
import me.jet.zhu.core.common.netty.cache.ISessionQueueCache;
import me.jet.zhu.core.common.netty.dto.IMRequest;
import me.jet.zhu.core.common.netty.dto.IMSession;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.io.SimpleChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.InetSocketAddress;

@Service
public class NettyDispatchHandler extends SimpleChannelInboundHandlerAdapter<String> {
    private static final Logger logger = LoggerFactory.getLogger(NettyDispatchHandler.class);

    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, String iMessage) throws Exception {
        logger.info("#We got message:{}", iMessage);
        final IMSession session = new IMSession();
        final Channel channel = channelHandlerContext.channel();

        {
            final InetSocketAddress socketAddress = (InetSocketAddress) channel.remoteAddress();
            final InetAddress address = socketAddress.getAddress();
            final String host = address.getHostAddress();
            final int port = socketAddress.getPort();
            session.setHost0(host);
            session.setPort0(port);
            logger.info("#Remote host:{},port:{}", host, port);
        }
        {
            final InetSocketAddress socketAddress = (InetSocketAddress) channel.localAddress();
            final InetAddress address = socketAddress.getAddress();
            final String host = address.getHostAddress();
            final int port = socketAddress.getPort();
            session.setHost1(host);
            session.setPort1(port);
            logger.info("#Native host:{},port:{}", host, port);
        }


        try {
            final IMRequest request = JSONUtil.toBean(iMessage, IMRequest.class);
            session.setRequest(request);
            final Integer tp = request.getHead().getTp();
            session.setTp(tp);
            switch (tp) {
                //Common Message
                case 0:
                    ISessionQueueCache.getInstance().add(session);
                    break;
                //Control Message
                case 1:
                    IControlQueueCache.getInstance().add(session);
                    //todo
                    break;
                default:
                    throw new RuntimeException("Protocol is not supported！" + tp);
            }
            final String oMessage = JSONUtil.toJsonStr(session.getResponse());
            if (StringUtils.isNoneBlank(oMessage)) {
                throw new RuntimeException("返回值异常" + oMessage);
            }
            logger.info("We send back a message:{}", oMessage);
            channelHandlerContext.writeAndFlush(oMessage);
        } catch (Exception ex) {
            logger.error("Message process exception：{}", ex);
            channelHandlerContext.close();
        }
    }
}