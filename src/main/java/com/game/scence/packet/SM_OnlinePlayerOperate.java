package com.game.scence.packet;

import com.game.scence.packet.bean.PlayerPositionVO;

import java.util.List;

/**
 * @Author：xuxin
 * @Date: 2019/6/11 21:16
 */
public class SM_OnlinePlayerOperate {
    /**所以玩家的位置信息1,2:1,2*/

    private List<PlayerPositionVO> playerPositionVOList;

    public List<PlayerPositionVO> getPlayerPositionVOList() {
        return playerPositionVOList;
    }

    public void setPlayerPositionVOList(List<PlayerPositionVO> playerPositionVOList) {
        this.playerPositionVOList = playerPositionVOList;
    }
}
