package com.socket.core;

import com.game.SpringContext;
import com.socket.core.session.SessionUtil;
import com.socket.core.session.TSession;
import com.socket.dispatcher.config.RegistSerializerMessage;
import com.socket.dispatcher.core.ActionDispatcher;
import com.socket.dispatcher.executor.IdentifyThreadPoolExecutor;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import org.msgpack.MessagePack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class EchoServerHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(ChannelInboundHandlerAdapter.class);
    /**
     * 因为多线程，所以使用原子操作类来进行计数
     */
    private static AtomicInteger atomicInteger = new AtomicInteger();
    private static final IdentifyThreadPoolExecutor identifyThreadPoolExecutor = new IdentifyThreadPoolExecutor();
    private static final ActionDispatcher actionDispatcher = new ActionDispatcher(identifyThreadPoolExecutor);
    /**
     * 收到客户端消息，自动触发
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        if(logger.isDebugEnabled()){
            logger.debug((atomicInteger.addAndGet(1)) + "--->" + Thread.currentThread().getName() + ",The server receive  order : " + msg);
        }
        atomicInteger.addAndGet(1);

        List<Object> objects =(List<Object>)msg;
        if(objects.size()<=0){
            logger.error("错了错了，传来的包为空！");
            return;
        }
        String opIndex = objects.get(0).toString();
        Object packet = objects.get(1);
        String time = objects.get(2).toString();
        TSession session = SessionUtil.getChannelSession(ctx.channel());
        Class<?> aClass = RegistSerializerMessage.idClassMap.get(opIndex);
        byte[] unpack = MessagePack.unpack(MessagePack.pack(packet), byte[].class);

        //分发处理
        actionDispatcher.doHandle(session,Integer.parseInt(opIndex),unpack,Long.parseLong(time));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        if(!SessionUtil.createChannelSession(ctx.channel(), actionDispatcher)){
            ctx.channel().close();
            return;
        }
        TSession session = SessionUtil.getChannelSession(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.info("客户端关闭:" + ctx.channel().remoteAddress());
        /**当发生异常时，关闭 ChannelHandlerContext，释放和它相关联的句柄等资源 */
        //cause.printStackTrace();

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

        super.channelInactive(ctx);
    }
}
