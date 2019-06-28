package com.game.register.service;

import com.game.SpringContext;
import com.game.user.account.entity.AccountEnt;
import com.game.register.packet.SM_Register;
import com.game.user.account.model.AccountInfo;
import com.game.util.MD5Util;
import com.socket.core.session.TSession;
import io.netty.channel.Channel;
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
    public void doRegister(TSession session,String username, String passward) {
        int serverId = SpringContext.getServerConfigValue().getServerId();
        String usernameDB = username+"_"+serverId;
        AccountEnt accountEnt = SpringContext.getAccountService().getAccountEnt(usernameDB);
        String passwardDB = MD5Util.inputPassToDbPass(passward, MD5Util.SALTDB);
        if(accountEnt==null){
            accountEnt = new AccountEnt();
            accountEnt.setAccountId(usernameDB);
            accountEnt.setPassward(passwardDB);
            accountEnt.setAccountInfo(AccountInfo.valueOf(null));
            String ip = SpringContext.getServerConfigValue().getIp();
            Channel channel = session.getChannel();
            if(session.getInetIp().equals(ip)||session.getIp().equals(channel.remoteAddress().toString())){
                accountEnt.getAccountInfo().setGm(true);
            }
            SpringContext.getAccountService().save(accountEnt);
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
