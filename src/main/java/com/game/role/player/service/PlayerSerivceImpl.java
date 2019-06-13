package com.game.role.player.service;

import com.db.HibernateDao;
import com.game.SpringContext;
import com.game.base.gameObject.constant.ObjectType;
import com.game.user.account.entity.AccountEnt;
import com.game.user.account.model.AccountInfo;
import com.game.role.player.entity.PlayerEnt;
import com.game.role.player.model.Player;
import com.game.role.constant.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author：xuxin
 * @Date: 2019/6/3 11:36
 */
@Component
public class PlayerSerivceImpl implements PlayerService {
    private static Logger logger = LoggerFactory.getLogger(PlayerSerivceImpl.class);
    @Autowired
    private HibernateDao hibernateDao;
    @Override
    public void save(PlayerEnt playerEnt) {
        hibernateDao.update(PlayerEnt.class, playerEnt);
    }

    @Override
    public PlayerEnt getPlayer(long playerId) {
        PlayerEnt playerEnt = hibernateDao.find(PlayerEnt.class, playerId);
        playerEnt.doDeserialize();
        return playerEnt;
    }

    @Override
    public void insert(PlayerEnt playerEnt) {
        hibernateDao.save(PlayerEnt.class, playerEnt);
    }

    @Override
    public PlayerEnt createPlayer(String accountId, Job type) {
        PlayerEnt playerEnt = new PlayerEnt();
        long playerId = SpringContext.getIdentifyService().getNextIdentify(ObjectType.PLAYER);
        playerEnt.setPlayerId(playerId);
        playerEnt.setAccountId(accountId);
        Player player = Player.valueOf(playerId, accountId, type);
        player.setPlayerName(player.getPlayerName()+getPlayerJobNum(accountId,type));
        playerEnt.setPlayer(player);
        insert(playerEnt);
        return playerEnt;
    }

    private int getPlayerJobNum(String accountId, Job job){
        AccountEnt accountEnt = SpringContext.getAccountService().getAccountEnt(accountId);
        List<Long> playerIds = accountEnt.getAccountInfo().getPlayerIds();
        if(playerIds==null){
            return 1;
        }
        int num = 1;
        for (long playerId: playerIds){
            PlayerEnt playerEnt = SpringContext.getPlayerSerivce().getPlayer(playerId);
            Player player = playerEnt.getPlayer();
            if(player.getCareer()==job.getJobType()){
                num++;
            }
        }
        return num;
    }
    @Override
    public List<Player> getPlayer(String accountId){
        AccountEnt accountEnt = SpringContext.getAccountService().getAccountEnt(accountId);
        AccountInfo accountInfo = accountEnt.getAccountInfo();
        List<Long> playerIds = accountInfo.getPlayerIds();
        if(playerIds==null||playerIds.size()==0){
            logger.warn("玩家{}没有角色信息",accountId);
            return null;
        }
        List<Player> players = new ArrayList<>();
        for(long playerId:playerIds){
            PlayerEnt playerEnt = SpringContext.getPlayerSerivce().getPlayer(playerId);
            Player player = playerEnt.getPlayer();
            players.add(player);
        }
        return players;
    }
}
