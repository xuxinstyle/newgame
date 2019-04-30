package nettyMsgTest.echo;

import JsonUtil.JsonUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.ScheduledFuture;
import nettyMsgTest.domain.CM_Test1;
import nettyMsgTest.domain.User;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
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
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println((atomicInteger.addAndGet(1)) + "--->" + Thread.currentThread().getName() + ",The server receive  order : " + msg);

        //DeviceSession session = ctx.channel().attr(KEY).get();     // 检测是否 自己注册的 客户端


        /**
         * 如果传输的是 POJO 对象，则可以转成 List<Object>
         * list 中的每一个元素都是发送来的 POJO 对象的属性值
         * 注意：如果对方传输只是简单的 String 对象，则不能强转为 List<Object>
         */
       /* System.out.println("转换msg对象");
        User user= (User)msg;
        System.out.println("强制类型转换为User："+user.toString());*/
        /*List<User> users = JsonTools.toList(msg, User.class);
        for (User user : users) {
            System.out.println("属性：" + user);
        }*/
        //CMessagePack cmsg = (CMessagePack)msg;
        List<Object> objects =(List<Object>)msg;
        Object o = objects.get(0);

        String o1 = objects.get(1).toString();
        System.out.println("强制类型转换："+o1);
        for (Object obj : objects) {
            //int i = Integer.parseInt(obj.toString());
            //System.out.println(i);
            System.out.println("属性：" + obj);
        }
        //在这个地方处理从客户端接收到的消息
        //并在这里返回
        /**
         * 服务端接收到客户端发送来的数据后，再回发给客户端
         */
        ctx.writeAndFlush(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("-----客户端关闭:" + ctx.channel().remoteAddress());
        /**当发生异常时，关闭 ChannelHandlerContext，释放和它相关联的句柄等资源 */
        cause.printStackTrace();
        ctx.close();
    }
}
