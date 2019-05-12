
* 参考链接 
* https://blog.csdn.net/byxdaz/article/details/78694064
* https://www.jianshu.com/p/f7c180b16a12
### MessagePack常用api
## 序列化反序列化
* MessagePack mpPack = new MessagePack();
* byte [] raw = mpPack.write(obj);
* msgpack.read(b)
## msg to Class
* class t = MessagePack.unpack(MessagePack.pack(msg),clazz);
