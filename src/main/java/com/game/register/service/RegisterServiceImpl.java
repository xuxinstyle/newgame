package com.game.register.service;

import com.game.SpringContext;
import com.game.role.account.entity.AccountEnt;
import com.game.register.packet.SM_Register;
import com.socket.core.session.TSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Author：xuxin
 * @Date: 2019/5/29 18:50
 */
@Component
public class RegisterServiceImpl implements RegisterService {

    private static final Logger loggger = LoggerFactory.getLogger(RegisterServiceImpl.class);
    @Override
    public void doRegister(String username, String passward, TSession session) {
        AccountEnt accountEnt = SpringContext.getAccountService().getAccountEnt(username);

        if(accountEnt==null){
            SpringContext.getAccountService().insert(username,passward);
            SM_Register sm = new SM_Register();
            sm.setStatus(true);
            session.sendPacket(sm);
            loggger.info("["+username+"]注册成功");
        }else{
            SM_Register sm = new SM_Register();
            sm.setStatus(false);
            session.sendPacket(sm);
            loggger.warn("["+username+"注册失败");
        }

    }
}
