package com.game.login.facade;

import com.game.login.packet.CM_Login;
import com.socket.core.TSession;
import com.socket.dispatcher.anno.HandlerAnno;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;


/**
 * @Author：xuxin
 * @Date: 2019/4/30 10:21
 */
@Component
public class LoginFacade {
    Logger logger = Logger.getLogger(LoginFacade.class);
    @HandlerAnno
    public void login(TSession session, CM_Login req){
        System.out.println("进入到了facade中的login方法");
        logger.info("进入到了facade中的login方法");
    }
}
