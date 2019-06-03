package com.game.base.account.service;

import com.game.base.account.entity.AccountEnt;
import com.game.base.account.entity.AccountExample;
import com.game.base.account.mapper.AccountMapper;
import com.game.base.account.model.AccountInfo;
import com.game.base.account.packet.SM_CreatePlayer;
import com.game.scence.constant.SceneType;
import com.game.scence.packet.SM_EnterInitScence;
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
    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
    @Autowired
    private AccountMapper accountMapper;

    @Override
    public void insert(String username, String passward) {
        AccountEnt accountEnt = new AccountEnt();
        accountEnt.setAccountId(username);
        accountEnt.setPassward(passward);
        accountEnt.setAccountInfo(AccountInfo.valueOf());
        accountEnt.doSerialize();
        accountMapper.insert(accountEnt);
    }


    @Override
    public AccountEnt getAccountEnt(String accountId) {
        AccountEnt accountEnt = accountMapper.selectByPrimaryKey(accountId);
        accountEnt.doDeserialize();

        return accountEnt;
    }

    @Override
    public void createPlayer(TSession session, String nickName, String career, String accountId) {
        System.out.println("nickName:"+nickName+"  career:"+career+"  accountId:"+accountId);
        AccountEnt accountEnt = getAccountEnt(accountId);
        AccountInfo accountInfo = accountEnt.getAccountInfo();
        System.out.println(accountInfo.toString());
        accountInfo.setNickName(nickName);
        accountInfo.setCareer(career);
        accountInfo.setSurviveStatus(1);
        accountInfo.setCurrentMapType(SceneType.NoviceVillage);
        save(accountEnt);
        SM_CreatePlayer sm = new SM_CreatePlayer();
        sm.setAccountId(accountId);
        sm.setStatus(1);
        session.sendPacket(sm);
        /**
         * 请求进入场景地图
         */
        SM_EnterInitScence res = new SM_EnterInitScence();
        res.setAccountId(accountId);
        res.setType(accountInfo.getCurrentMapType());
        session.sendPacket(res);
    }

    @Override
    public void save(AccountEnt accountEnt) {
        accountEnt.doSerialize();
        accountMapper.updateByPrimaryKey(accountEnt);
    }


}
