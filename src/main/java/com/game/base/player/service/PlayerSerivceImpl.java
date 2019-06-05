package com.game.base.player.service;

import com.game.SpringContext;
import com.game.base.player.entity.PlayerEnt;
import com.game.base.player.mapper.PlayerMapper;
import com.game.base.player.model.Player;
import com.game.role.constant.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        playerMapper.updateByPrimaryKey(playerEnt);
    }

    @Override
    public PlayerEnt getPlayer(long playerId) {
        PlayerEnt playerEnt = playerMapper.selectByPrimaryKey(playerId);
        playerEnt.doDeserialize();
        return playerEnt;
    }

    @Override
    public void insert(PlayerEnt playerEnt) {
        playerEnt.doSerialize();
        playerMapper.insert(playerEnt);
    }

    @Override
    public PlayerEnt createPlayer(String accountId, Job type) {
        PlayerEnt playerEnt = new PlayerEnt();
        long playerId = SpringContext.getIdentifyService().getNextIdentify();
        playerEnt.setPlayerId(playerId);
        playerEnt.setAccountId(accountId);
        Player player = Player.valueOf(playerId, accountId, type);
        playerEnt.setPlayer(player);
        insert(playerEnt);
        return playerEnt;
    }
}
