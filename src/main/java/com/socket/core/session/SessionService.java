package com.socket.core.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author：xuxin
 * @Date: 2019/6/17 16:32
 */
@Component
public class SessionService {
    @Autowired
    private SessionManager  sessionManager;

}
