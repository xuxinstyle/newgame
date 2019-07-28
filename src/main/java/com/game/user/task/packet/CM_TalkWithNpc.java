package com.game.user.task.packet;

/**
 * 请求与npc对话
 *
 * @Author：xuxin
 * @Date: 2019/7/25 22:38
 */
public class CM_TalkWithNpc {
    /**
     * npcid
     */
    private int npcId;
    /**
     * 地图id
     */
    private int mapId;

    public int getNpcId() {
        return npcId;
    }

    public void setNpcId(int npcId) {
        this.npcId = npcId;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }
}
