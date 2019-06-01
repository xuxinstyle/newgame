package com.game.login.service;

import com.game.SpringContext;
import com.game.base.account.entity.AccountEnt;
import com.game.login.packet.SM_Login;
import com.socket.core.TSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author：xuxin
 * @Date: 2019/6/1 15:58
 */
@Component
public class LoginServiceImpl implements LoginService {

    private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);
    @Override
    public void login(TSession session, String username, String passward) {
        List<AccountEnt> select = SpringContext.getAccountService().select(username);

        for (AccountEnt accountEnt :select){
            if(accountEnt.getPassward().isEmpty()){
                return;
            }
            if(accountEnt.getPassward().equals(passward)){
                logger.info(accountEnt.getAccountId()+"登录成功！");
                //进入场景地图 如果玩家没有昵称就显示取昵称的界面
                SM_Login sm = new SM_Login();
                sm.setStatus(1);
                session.sendPacket(sm);
            }else{
                logger.info(accountEnt.getAccountId()+"密码错误！");
                SM_Login sm = new SM_Login();
                sm.setStatus(0);
                session.sendPacket(sm);
            }
        }
    }
}
