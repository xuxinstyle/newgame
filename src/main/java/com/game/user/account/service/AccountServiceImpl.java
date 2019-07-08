package com.game.user.account.service;

import com.db.cache.EntityCacheService;
import com.game.SpringContext;
import com.game.user.account.entity.AccountEnt;

import com.game.user.account.model.AccountInfo;
import com.game.user.account.packet.SM_CreatePlayer;
import com.game.role.player.entity.PlayerEnt;
import com.game.scence.visible.constant.MapType;
import com.socket.core.session.TSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @Author：xuxin
 * @Date: 2019/5/29 19:31
*/

@Component
public class AccountServiceImpl implements AccountService {

    @Autowired
    private EntityCacheService<String, AccountEnt> entityCacheService;

    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);


    @Override
    public void realRegister(String username, String passward) {
        AccountEnt accountEnt = new AccountEnt();
        accountEnt.setAccountId(username);
        accountEnt.setPassward(passward);
        accountEnt.setAccountInfo(AccountInfo.valueOf(null));
        entityCacheService.saveOrUpdate(accountEnt);

    }


    @Override
    public AccountEnt getAccountEnt(String accountId) {
        AccountEnt accountEnt = entityCacheService.find(AccountEnt.class, accountId);

        if(accountEnt==null){
            logger.warn("数据库中没有["+accountId+"]的账号信息");
            return null;
        }
        return accountEnt;
    }

    @Override
    public void createFirstPlayer(TSession session, String nickName, int type, String accountId) {

        AccountEnt accountEnt = getAccountEnt(accountId);
        AccountInfo accountInfo = accountEnt.getAccountInfo();
        accountInfo.setAccountName(nickName);
        PlayerEnt playerEnt = SpringContext.getPlayerSerivce().createPlayer(accountId, type,nickName);
        long playerId = playerEnt.getPlayerId();
        accountInfo.setPlayerId(playerId);
        SpringContext.getItemService().createStorage(accountId);
        accountEnt.setAccountInfo(accountInfo);

        save(accountEnt);
        SM_CreatePlayer sm = new SM_CreatePlayer();
        sm.setAccountId(accountId);
        sm.setStatus(1);
        sm.setPlayerId(playerId);
        session.sendPacket(sm);
        /**
         * 请求进入场景地图
         */
        SpringContext.getScenceSerivce().enterMap(session,accountId,MapType.NoviceVillage.getMapId());
    }

    @Override
    public void save(AccountEnt accountEnt) {
        if(accountEnt==null){
            logger.error("accountEnt为空，持久化失败");
            return ;
        }
        entityCacheService.saveOrUpdate(accountEnt);
        //hibernateDao.saveOrUpdate(AccountEnt.class,accountEnt);
    }


}
