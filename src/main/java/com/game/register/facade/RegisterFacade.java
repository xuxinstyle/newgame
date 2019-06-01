package com.game.register.facade;

import com.game.SpringContext;
import com.game.register.packet.CM_Register;
import com.socket.core.TSession;
import com.socket.dispatcher.anno.HandlerAnno;
import org.springframework.stereotype.Component;

/**
 * @Author：xuxin
 * @Date: 2019/5/29 18:42
 */
@Component
public class RegisterFacade {
    @HandlerAnno
    public void register(TSession session, CM_Register req){
        try {
            SpringContext.getRegisterService().doRegister(req.getUsername(), req.getPassward(), session);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

