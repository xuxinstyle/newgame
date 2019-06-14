## MessagePack、发包、编码、解码说明
### MessagePack
它像 JSON，但是更快更小。
MessagePack 是一种高效的二进制序列化格式。它允许您在 JSON 等多种语言之间交换数据，
但它更快速更小巧。小整数被编码为单个字节，典型的短字符串除了字符串本身之外只需要
一个额外的字节。
#### 序列化反序列化
* MessagePack mpPack = new MessagePack();
* byte [] raw = mpPack.write(obj);
* msgpack.read(b)
#### msg to Class
* class t = MessagePack.unpack(MessagePack.pack(msg),clazz);
messagepack.read();
### 发包
  * LengthFieldPrepender：前置长度域编码器——放在MsgpackEncoder编码器前面
 LengthFieldPrepender()会在编码之前将需要编码的包前加一个指定长度为的字段，该字段存放包的长度加上本身长度（一般为2）
  * LengthFieldBasedFrameDecoder：固定长度解码器——放在MsgpackDecoder解码器前面
 LengthFieldBasedFrameDecoder会在解码之前只截取固定长度的字段，即之前编码时放进去的长度
 再读取该长度的包