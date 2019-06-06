package com.game.role.player.service;

import com.game.SpringContext;
import com.game.base.gameObject.constant.EntityType;
import com.game.role.account.entity.AccountEnt;
import com.game.role.player.entity.PlayerEnt;
import com.game.role.player.mapper.PlayerMapper;
import com.game.role.player.model.Player;
import com.game.role.constant.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author：xuxin
 * @Date: 2019/6/3 11:36
 */
@Component
public class PlayerSerivceImpl implements PlayerService {
    @Autowired
    private PlayerMapper playerMapper;

    @Override
    public void save(PlayerEnt playerEnt) {
        playerEnt.doSerialize();
        playerMapper.updatePlayerEnt(playerEnt);
    }

    @Override
    public PlayerEnt getPlayer(long playerId) {
        PlayerEnt playerEnt = playerMapper.selectPlayerEnt(playerId);
        playerEnt.doDeserialize();
        return playerEnt;
    }

    @Override
    public void insert(PlayerEnt playerEnt) {
        playerEnt.doSerialize();
        playerMapper.insertPlayerEnt(playerEnt);
    }

    @Override
    public PlayerEnt createPlayer(String accountId, Job type) {
        PlayerEnt playerEnt = new PlayerEnt();
        long playerId = SpringContext.getIdentifyService().getNextIdentify(EntityType.PLAYER);
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
        int num = 0;
        for (long playerId: playerIds){
            PlayerEnt playerEnt = SpringContext.getPlayerSerivce().getPlayer(playerId);
            Player player = playerEnt.getPlayer();
            if(player.getCareer()==job.getJobType()){
                num++;
            }
        }
        return num;
    }
}
