package com.game.register.service;

import com.game.SpringContext;
import com.game.base.account.entity.AccountEnt;
import com.game.register.packet.SM_Register;
import com.socket.core.TSession;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author：xuxin
 * @Date: 2019/5/29 18:50
 */
@Component
public class RegisterServiceImpl implements RegisterService {

    @Override
    public void doRegister(String username, String passward, TSession session) {
        List<AccountEnt> accountEnts = SpringContext.getAccountService().select(username);
        if(accountEnts==null||accountEnts.size()==0){
            SpringContext.getAccountService().insert(username,passward);
            SM_Register sm = new SM_Register();
            sm.setStatus(true);
            session.sendPacket(sm);
        }else{
            SM_Register sm = new SM_Register();
            sm.setStatus(false);
            session.sendPacket(sm);
        }

    }
}
