package com.socket.core;

import com.game.SpringContext;
import com.socket.core.session.SessionManager;
import com.socket.core.session.SessionUtil;
import com.socket.core.session.TSession;
import com.socket.dispatcher.core.ActionDispatcher;
import com.socket.dispatcher.executor.DistributionThreadPoolExecutor;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import org.msgpack.MessagePack;
import org.msgpack.type.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class EchoServerHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(ChannelInboundHandlerAdapter.class);

    private ActionDispatcher actionDispatcher = new ActionDispatcher();
    /**
     * 收到客户端消息，自动触发
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        MyPack pack = (MyPack) msg;
        if(pack==null||pack.getpId()==null||pack.getPacket()==null){
            logger.error("传来的空包...");
            return;
        }
        //logger.info("线程："+Thread.currentThread().getName());
        TSession session = SessionUtil.getChannelSession(ctx.channel());
        if(session==null){
            return;
        }
        //分发处理
        actionDispatcher.handle(session,pack.getpId(),pack.getPacket());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        if(!SessionUtil.createChannelSession(ctx.channel(), actionDispatcher)){
            ctx.channel().close();
            return;
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.info("客户端关闭:" + ctx.channel().remoteAddress());
        /**当发生异常时，关闭 ChannelHandlerContext，释放和它相关联的句柄等资源 */
        // cause.printStackTrace();
        ctx.channel().close();


    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        TSession session = SessionUtil.getChannelSession(ctx.channel());
        String accountId = session.getAccountId();
        if(accountId!=null){
            SpringContext.getLoginService().logout(session,accountId);
        }
        super.channelInactive(ctx);
    }
}
