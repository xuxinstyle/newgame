package com.socket.heartBeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @Author：xuxin
 * @Date: 2019/6/1 16:36
 */
public class IMIdleStateHandler extends IdleStateHandler {
    private static final Logger logger = LoggerFactory.getLogger(IMIdleStateHandler.class);
    private static final int READER_IDLE_TIME = 30;
    public IMIdleStateHandler() {
        super(READER_IDLE_TIME,0,0,TimeUnit.SECONDS);
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
        logger.info(READER_IDLE_TIME+"秒内未读到数据关闭连接");
        ctx.channel().close();
    }
}
