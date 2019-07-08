package com.game.scence.field.service;

/**
 * @Author：xuxin
 * @Date: 2019/7/4 9:53
 */
public interface FieldService {
    /**
     * 查看怪物信息
     * @param mapId
     * @param monsterObjectId
     */
    void showMonsterInfo(String accountId,int mapId, long monsterObjectId);
}
