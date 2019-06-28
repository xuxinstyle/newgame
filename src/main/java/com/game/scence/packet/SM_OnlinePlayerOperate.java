package com.game.scence.packet;

import com.game.scence.model.PlayerPosition;

import java.util.List;

/**
 * @Author：xuxin
 * @Date: 2019/6/11 21:16
 */
public class SM_OnlinePlayerOperate {
    /**所以玩家的位置信息1,2:1,2*/

    private List<PlayerPosition> playerPositionList;

    public List<PlayerPosition> getPlayerPositionList() {
        return playerPositionList;
    }

    public void setPlayerPositionList(List<PlayerPosition> playerPositionList) {
        this.playerPositionList = playerPositionList;
    }
}
