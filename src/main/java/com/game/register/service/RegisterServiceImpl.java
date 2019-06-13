package com.game.register.service;

import com.game.SpringContext;
import com.game.user.account.entity.AccountEnt;
import com.game.register.packet.SM_Register;
import com.game.util.MD5Util;
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
        int serverId = SpringContext.getServerConfigValue().getServerId();
        String usernameDB = username+"_"+serverId;
        AccountEnt accountEnt = SpringContext.getAccountService().getAccountEnt(usernameDB);
        String passwardDB = MD5Util.inputPassToDbPass(passward, MD5Util.saltDB);
        if(accountEnt==null){
            SpringContext.getAccountService().insert(usernameDB,passwardDB);
            SM_Register sm = new SM_Register();
            sm.setStatus(true);
            session.sendPacket(sm);
            loggger.info("["+usernameDB+"]注册成功");
        }else{
            SM_Register sm = new SM_Register();
            sm.setStatus(false);
            session.sendPacket(sm);
            loggger.warn("["+usernameDB+"注册失败");
        }

    }
}
