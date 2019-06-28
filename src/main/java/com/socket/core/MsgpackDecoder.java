package com.socket.core;

import com.socket.core.session.SessionUtil;
import com.socket.core.session.TSession;
import com.socket.utils.JsonUtils;
import com.socket.utils.ProtoStuffUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.msgpack.MessagePack;
import org.msgpack.type.Value;
import sun.security.krb5.internal.PAData;

import java.util.List;
/**
 * MessagePack 解码器 - 继承 Netty 的 MessageToMessageDecoder,并重写方法
 * @Author：xuxin
 * @Date: 2019/6/17 12:30
 */
public class MsgpackDecoder extends MessageToMessageDecoder<ByteBuf> {

    /**
     *  重写方法，首先从数据报 byteBuf 中获取需要解码的 byte 数组，
     *  然后调用 MessagePack 的 read 方法将其反序列化为 Object 对象，将解码后的对象加入到解码列表 list 中，
     *  这样就完成了 MessagePack 的解码操作
     * @param channelHandlerContext
     * @param byteBuf
     * @param list
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {

        int length = byteBuf.readableBytes();
        byte[] array = new byte[length];
        byteBuf.getBytes(byteBuf.readerIndex(), array, 0, length);
        MyPack myPack = ProtoStuffUtil.deserializer(array, MyPack.class);
        list.add(myPack);

    }
}
