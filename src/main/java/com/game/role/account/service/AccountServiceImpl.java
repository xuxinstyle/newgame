package com.game.role.account.service;

import com.db.HibernateDao;
import com.game.SpringContext;
import com.game.role.account.entity.AccountEnt;
import com.game.role.account.mapper.AccountMapper;
import com.game.role.account.model.AccountInfo;
import com.game.role.account.packet.SM_CreatePlayer;
import com.game.role.player.entity.PlayerEnt;
import com.game.role.constant.Job;
import com.game.scence.constant.SceneType;
import com.socket.core.session.TSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 * @Author：xuxin
 * @Date: 2019/5/29 19:31
*/

@Component
public class AccountServiceImpl implements AccountService {
    @Autowired
    private HibernateDao hibernateDao;
    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
    //@Autowired
    //private AccountMapper accountMapper;

    @Override
    public void insert(String username, String passward) {
        AccountEnt accountEnt = new AccountEnt();
        accountEnt.setAccountId(username);
        accountEnt.setPassward(passward);
        accountEnt.setAccountInfo(AccountInfo.valueOf(null));
        accountEnt.doSerialize();
        hibernateDao.save(AccountEnt.class, accountEnt);
        //accountMapper.insertAccountEnt(accountEnt);
    }


    @Override
    public AccountEnt getAccountEnt(String accountId) {
        AccountEnt accountEnt = hibernateDao.find(AccountEnt.class, accountId);
        //AccountEnt accountEnt = accountMapper.selectAccountEnt(accountId);
        if(accountEnt==null){
            logger.warn("数据库中没有["+accountId+"]的账号信息");
            return null;
        }
        accountEnt.doDeserialize();
        if(logger.isDebugEnabled()){
            logger.debug(accountEnt.getAccountInfo().toString());
            logger.debug(accountEnt.toString());
        }
        return accountEnt;
    }

    @Override
    public void createPlayer(TSession session, String nickName, int type, String accountId) {

        AccountEnt accountEnt = getAccountEnt(accountId);
        AccountInfo accountInfo = AccountInfo.valueOf(nickName);
        List<Long> playerIds = accountInfo.getPlayerIds();
        PlayerEnt player = SpringContext.getPlayerSerivce().createPlayer(accountId, Job.valueOf(type));
        playerIds.add(player.getPlayerId());
        accountEnt.setAccountInfo(accountInfo);
        save(accountEnt);
        SM_CreatePlayer sm = new SM_CreatePlayer();
        sm.setAccountId(accountId);
        sm.setStatus(1);
        session.sendPacket(sm);
        /**
         * 请求进入场景地图
         */
        SpringContext.getScenceSerivce().enterMap(session,accountId,SceneType.NoviceVillage.getMapid());
    }

    @Override
    public void save(AccountEnt accountEnt) {
        if(accountEnt==null){
            logger.error("accountEnt为空，持久化失败");
            return ;
        }
        accountEnt.doSerialize();
        if(logger.isDebugEnabled()){
            logger.debug(accountEnt.toString());
            logger.debug(accountEnt.getAccountInfo().toString());
        }

        hibernateDao.saveOrUpdate(AccountEnt.class,accountEnt);
        //accountMapper.updateAccountEnt(accountEnt);
        AccountEnt accountEnt1 = getAccountEnt(accountEnt.getAccountId());
        if(logger.isDebugEnabled()){
            logger.debug(accountEnt1.toString());
        }
    }


}
