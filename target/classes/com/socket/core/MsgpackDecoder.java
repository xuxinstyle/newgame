package com.socket.core;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.msgpack.MessagePack;

import java.util.List;

/**
 * MessagePack 解码器 - 继承 Netty 的 MessageToMessageDecoder,并重写方法
 */
public class MsgpackDecoder extends MessageToMessageDecoder<ByteBuf> {
   /* private Class<?> genericClass;
    public MsgpackDecoder(Class<?> genericClass) {
        this.genericClass = genericClass;
    }*/

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
        /*int length = byteBuf.readableBytes();
        byte[] array = new byte[length];

        String dataString = array.toString();
        JSONObject jsonObject = JSONObject.fromObject(dataString);

        CM_Test1 test1 = JsonTools.toBean(jsonObject, CM_Test1.class);
        *//*JsonTools.

        byteBuf.getBytes(byteBuf.readerIndex(), array, 0, length);*//*

        CMessagePack msg = new CMessagePack(byteBuf.readerIndex(),test1,length, System.nanoTime());
        MessagePack messagePack = new MessagePack();
        list.add(messagePack.read(array));*/

        /*int dataLength = byteBuf.readInt();//这边读取的第一个是长度字段
        byte[] data = new byte[dataLength];
        byteBuf.readBytes(data);//将in中的数据写入data
        Object obj = ProtoStuffUtil.deserializer(data, genericClass);
        list.add(obj)*/;
        int length = byteBuf.readableBytes();
        byte[] array = new byte[length];
        byteBuf.getBytes(byteBuf.readerIndex(), array, 0, length);

        MessagePack messagePack = new MessagePack();
        list.add(messagePack.read(array));

    }
}
