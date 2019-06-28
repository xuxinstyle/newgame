package com.socket.core;

import com.socket.utils.JsonUtils;
import com.socket.utils.ProtoStuffUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.msgpack.MessagePack;

/**
 * MessagePack 编码器 —— 继承 Netty 的 MessageToByteEncoder，比重写方法
 */
public class MsgpackEncoder extends MessageToByteEncoder<MyPack> {

    /**
     * 重写方法，负责将 Object 类型的 POJO 对象编码为 byte 数组，然后写入 ByteBuf 中
     * @param channelHandlerContext
     * @param o
     * @param byteBuf
     * @throws Exception
     */
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, MyPack o, ByteBuf byteBuf) throws Exception {
        byte[] serializer = ProtoStuffUtil.serializer(o);
        /*MessagePack messagePack = new MessagePack();
        byte[] raw = messagePack.write(o);*/
        byteBuf.writeBytes(serializer);
    }
}
