package com.socket.heartBeat;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Author：xuxin
 * @Date: 2019/6/1 17:25
 */
@ChannelHandler.Sharable
public class HeartBeatRequestHandler extends SimpleChannelInboundHandler<HeartBeatRequestPack> {
    public static final HeartBeatRequestHandler INSTANCE = new HeartBeatRequestHandler();

    private  HeartBeatRequestHandler(){

    }
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HeartBeatRequestPack msg) throws Exception {
        ctx.writeAndFlush(new HeartBeatResponsePack());
    }
}
