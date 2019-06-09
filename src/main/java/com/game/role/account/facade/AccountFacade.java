package com.game.role.account.facade;

import com.game.SpringContext;
import com.game.role.account.packet.CM_CreatePlayer;
import com.socket.core.session.TSession;
import com.socket.dispatcher.anno.HandlerAnno;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Author：xuxin
 * @Date: 2019/6/3 14:47
 */
@Component
public class AccountFacade {
    private static final Logger logger = LoggerFactory.getLogger(AccountFacade.class);
    @HandlerAnno
    public void createPlayer(TSession session, CM_CreatePlayer req){
        try {
            SpringContext.getAccountService().createPlayer(session, req.getNickName(), req.getCareer(), req.getAccountId());
        }catch (Exception e){
            e.printStackTrace();
            logger.error(req.getAccountId()+"创键角色失败"+e.toString());
        }
    }
}
