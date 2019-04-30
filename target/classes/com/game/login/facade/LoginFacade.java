package com.game.login.facade;

import com.game.login.packet.CM_Login;
import com.netty.core.TSession;
import com.netty.dispatcher.anno.HandlerAnno;
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
        logger.info("进入到了facade中的login方法");
    }
}
