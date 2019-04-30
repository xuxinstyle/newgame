package netty.core;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;


import java.util.concurrent.atomic.AtomicInteger;

public class EchoServerHandler extends ChannelInboundHandlerAdapter {
    /**
     * 因为多线程，所以使用原子操作类来进行计数
     */
    private static AtomicInteger atomicInteger = new AtomicInteger();

    /**
     * 收到客户端消息，自动触发
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg){
        try{
            /*System.out.println("接收客户端消息");
            System.out.println((atomicInteger.addAndGet(1)) + "--->" + Thread.currentThread().getName() + ",The server receive  order : " + msg);
            com.socket.core.CMessagePack mspack= (CMessagePack)msg;
            if(mspack.getMsgIndex() == 1){
                TestBean testBean = ProtoStuffUtil.deserializer(mspack.getDate(),TestBean.class);
                System.out.println(testBean.toString());
            }
            if(mspack.getMsgIndex() == 2){
                System.out.println(mspack.toString());
            }*/
            /*//返回消息给客户端
            ctx.writeAndFlush(mspack);*/
        }catch (Exception e){
            System.out.println("服务端channelRead异常");
            e.printStackTrace();
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        try{
            System.out.println("-----客户端关闭:" + ctx.channel().remoteAddress());
            /**当发生异常时，关闭 ChannelHandlerContext，释放和它相关联的句柄等资源 */
            cause.printStackTrace();
            ctx.close();
        }catch (Exception e){
            System.out.println("客户端关闭异常");
            e.printStackTrace();
        }
    }
}
