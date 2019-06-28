package com.socket.core.session;

import io.netty.channel.Channel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;


/**
 * @Author：xuxin
 * @Date: 2019/6/3 9:53
 */
public final class SessionUtil {
    private static final AttributeKey<TSession> SESSION_KEY = AttributeKey.valueOf("session-key");

    public static final boolean createChannelSession(Channel channel){
        Attribute<TSession> attr = channel.attr(SESSION_KEY);
        return attr.compareAndSet(null, new TSession(channel));
    }

    public static final TSession getChannelSession(Channel channel){
        Attribute<TSession> attr = channel.attr(SESSION_KEY);
        return attr.get();
    }


}
