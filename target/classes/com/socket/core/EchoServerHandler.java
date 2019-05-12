package com.socket.core;

import com.socket.dispatcher.core.ActionDispatcher;
import com.socket.dispatcher.executor.IdentifyThreadPoolExecutor;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class EchoServerHandler extends ChannelInboundHandlerAdapter {
    Logger logger = Logger.getLogger(EchoServerHandler.class);
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
        //System.out.println((atomicInteger.addAndGet(1)) + "--->" + Thread.currentThread().getName() + ",The server receive  order : " + msg);
        atomicInteger.addAndGet(1);
        /**
         * 如果传输的是 POJO 对象，则可以转成 List<Object>
         * list 中的每一个元素都是发送来的 POJO 对象的属性值
         * 注意：如果对方传输只是简单的 String 对象，则不能强转为 List<Object>
         */
        List<Object> objects =(List<Object>)msg;
        if(objects.size()<=0){
            logger.error("错了错了，传来的包为空！");
            return;
        }
        String opIndex = objects.get(0).toString();
        String time = objects.get(2).toString();
        TSession session = new TSession(ctx.channel(),actionDispatcher);
        actionDispatcher.doHandle(session,Integer.parseInt(opIndex),objects.get(1),Long.parseLong(time));
        /*for (Object obj : objects) {
            System.out.println("属性：" + obj);
        }*/
        //在这个地方处理从客户端接收到的消息
        //并在这里返回
        /**
         * 服务端接收到客户端发送来的数据后，再回发给客户端
         */
        //ctx.writeAndFlush(pack);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("-----客户端关闭:" + ctx.channel().remoteAddress());
        /**当发生异常时，关闭 ChannelHandlerContext，释放和它相关联的句柄等资源 */
        cause.printStackTrace();
        ctx.close();
    }
}
