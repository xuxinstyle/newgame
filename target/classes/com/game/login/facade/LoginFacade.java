package com.game.login.facade;

import com.game.SpringContext;
import com.game.connect.packet.CM_Connect;
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
        try {
            SpringContext.getLoginService().login(session, req.getAccountId(), req.getPassWord());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
