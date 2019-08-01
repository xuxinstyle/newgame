package com.game.scence.npc.service;

import com.game.scence.npc.resource.NpcResource;

import java.util.List;

/**
 * @Author：xuxin
 * @Date: 2019/7/3 11:21
 */
public interface NpcService {
    /**
     * 获取对于地图id的resource
     * @param mapId
     * @return
     */
    List<NpcResource> getAllMapNpcResource(int mapId);

    /**
     * 获取地图中npcids
     * @param mapId
     * @return
     */
    List<Integer> getNpcIds(int mapId);

    /**
     * 获取npc资源
     * @param id
     * @return
     */
    NpcResource getNpcResource(int id);

    /**
     * 做玩家与npc聊天操作 抛聊天事件
     *
     * @param accountId
     * @param mapId
     * @param npcId
     */
    void talkWithNpc(String accountId, int mapId, int npcId);
}
