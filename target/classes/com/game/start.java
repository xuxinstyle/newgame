package com.game;

import com.socket.core.EchoServerHandler;
import com.socket.core.MsgpackDecoder;
import com.socket.core.MsgpackEncoder;
import com.socket.dispatcher.config.RegistSerializerMessage;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class start {
    private static ClassPathXmlApplicationContext applicationContext;
    public static void main(String[] args) {
        applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        new RegistSerializerMessage().init();
        applicationContext.start();
        int port = SpringContext.getServerConfigValue().getPort();
        bind(port);
    }
    //开启服务器监听端口
    public static void bind(int port) {
        /**
         * interface EventLoopGroup extends EventExecutorGroup extends ScheduledExecutorService extends ExecutorService
         * 配置服务端的 NIO 线程池,用于网络事件处理，实质上他们就是 Reactor 线程组
         * bossGroup 用于服务端接受客户端连接，workerGroup 用于进行 SocketChannel 网络读写*/
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            /** ServerBootstrap 是 Netty 用于启动 NIO 服务端的辅助启动类，用于降低开发难度
             * */
            final ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    // 设置TCP连接超时时间
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            System.out.println(Thread.currentThread().getName() + ",服务器初始化通道...");
                            /**
                             * 为了处理半包消息，添加如下两个 Netty 内置的编解码器
                             * LengthFieldPrepender：前置长度域编码器——放在MsgpackEncoder编码器前面
                             * LengthFieldBasedFrameDecoder：长度域解码器——放在MsgpackDecoder解码器前面
                             * 关于 长度域编解码器处理半包消息，本文不做详细讲解，会有专门篇章进行说明
                             */
                            ch.pipeline().addLast("frameEncoder", new LengthFieldPrepender(2));
                            ch.pipeline().addLast("MessagePack encoder", new MsgpackEncoder());
                            ch.pipeline().addLast("frameDecoder", new LengthFieldBasedFrameDecoder(65535, 0, 2, 0, 2));
                            ch.pipeline().addLast("MessagePack Decoder", new MsgpackDecoder());
                            ch.pipeline().addLast(new EchoServerHandler());
                        }
                    });

            /**服务器启动辅助类配置完成后，调用 bind 方法绑定监听端口，调用 sync 方法同步等待绑定操作完成*/
            ChannelFuture f = b.bind(port).sync();

            System.out.println(Thread.currentThread().getName() + ",服务器开始监听端口，等待客户端连接.........");

            /**下面会进行阻塞，等待服务器连接关闭之后 main 方法退出，程序结束* */
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            /**优雅退出，释放线程池资源*/
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
