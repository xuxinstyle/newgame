package com.game.base.player.mapper;


import com.game.base.account.entity.AccountExample;
import com.game.base.player.entity.PlayerEnt;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PlayerMapper {
    
    int insert(PlayerEnt record);
    
    PlayerEnt selectByPrimaryKey(long player);
    
    int updateByPrimaryKey(PlayerEnt record);
}