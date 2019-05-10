package com.socket.Utils;

import com.socket.core.TSession;
import io.netty.channel.Channel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

/**
 * @Author：xuxin
 * @Date: 2019/4/29 14:43
 */
public class SessionUtil {
    private static final AttributeKey<TSession> SESSION_ATTRIBUTE_KEY = AttributeKey.valueOf("session-key");
    public static TSession getChannalSession(Channel channel) {
        Attribute<TSession> sessionAttribute = channel.attr(SESSION_ATTRIBUTE_KEY);
        return sessionAttribute.get();
    }
}
