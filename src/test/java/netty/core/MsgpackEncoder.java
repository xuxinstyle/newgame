package netty.core;

import com.socket.Utils.ProtoStuffUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * CMessagePack 编码器 —— 继承 Netty 的 MessageToByteEncoder，比重写方法
 */
public class MsgpackEncoder extends MessageToByteEncoder<Object> {

    /**
     * 重写方法，负责将 Object 类型的 POJO 对象编码为 byte 数组，然后写入 ByteBuf 中
     * @param channelHandlerContext
     * @param o
     * @param byteBuf
     * @throws Exception
     */
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf){
        try{
            //MessagePack messagePack = new MessagePack();
            System.out.println("开始编码：");

            byte[] serializer = ProtoStuffUtil.serializer(o);
            /** 序列化对象*/
            // byte[] raw = messagePack.write(o);
            byteBuf.writeBytes(serializer);
            System.out.println("编码完成");
        }catch (Exception e){
            System.out.println("服务端编码异常");
            e.printStackTrace();
        }


    }
}
