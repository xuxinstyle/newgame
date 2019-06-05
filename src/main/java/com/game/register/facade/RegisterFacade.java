package com.game.register.facade;

import com.game.SpringContext;
import com.game.register.packet.CM_Register;
import com.socket.core.session.TSession;
import com.socket.dispatcher.anno.HandlerAnno;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Author：xuxin
 * @Date: 2019/5/29 18:42
 */
@Component
public class RegisterFacade {
    private static final Logger logger = LoggerFactory.getLogger(RegisterFacade.class);
    @HandlerAnno
    public void register(TSession session, CM_Register req){
        try {
            SpringContext.getRegisterService().doRegister(req.getUsername(), req.getPassward(), session);
        }catch (Exception e){
            logger.error("["+req.getUsername()+"]注册失败");
            e.printStackTrace();
        }
    }
}

