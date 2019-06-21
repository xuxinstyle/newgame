package com.game.role.player.service;

import com.game.role.player.entity.PlayerEnt;
import com.game.role.constant.Job;
import com.game.role.player.model.Player;
import com.game.role.player.resource.PlayerLevelResource;
import com.socket.core.session.TSession;

import java.util.List;

public interface PlayerService {
    /**
     * 保存玩家数据
     * @param playerEnt
     */
    void save(PlayerEnt playerEnt);

    /**
     * 向数据库查询玩家数据
     * @param playerId
     * @return
     */
    PlayerEnt getPlayerEnt(long playerId);
    /**
     * 向数据库新增角色信息
     * @param playerEnt
     */
    void insert(PlayerEnt playerEnt);

    /**
     * 创建角色
     * @param accountId
     * @param type
     * @param nickName
     * @return
     */
    PlayerEnt createPlayer(String accountId, int type, String nickName);

    /**
     * 获取角色
     * @param accountId
     * @return
     */
    Player getPlayer(String accountId);

    /**
     * 获取PlayerLevelResource
     * @param id
     * @return
     */
    PlayerLevelResource getPlayerLevelResource(Object id);

    /**
     *  从数据库获取角色实体
     *
     * @param accountId
     * @return
     */

    PlayerEnt getPlayerEnt(String accountId);

    /**
     * 将玩家的属性信息返回给客户端
     * @param session
     * @param accountId
     */
    void showPlayerAttribute(TSession session, String accountId);


}
