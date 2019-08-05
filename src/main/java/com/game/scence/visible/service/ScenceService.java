package com.game.scence.visible.service;


import com.game.base.gameobject.constant.ObjectType;
import com.game.role.player.model.Player;
import com.game.scence.base.model.AbstractScene;
import com.game.scence.fight.model.PlayerUnit;
import com.game.scence.visible.model.Position;
import com.game.scence.visible.resource.MapResource;
import com.socket.core.session.TSession;

/**
 * @Author：xuxin
 * @Date: 2019/6/3 10:29
 */
public interface ScenceService {
    /**
     * 玩家刚创角时进入初始化场景
     * @param session
     * @param accountId
     */
    void loginAfterEnterMap(TSession session, String accountId, int mapId);

    /**
     * @param session
     * @param player
     * @param mapId
     * @param firstRequest
     */
    void doEnterMap(Player player, int mapId, boolean firstRequest);

    /**
     * 查看地图中所有的可视物信息
     * @param session
     * @param mapId
     */
    void showAllVisibleInfo(TSession session, int mapId);

    /**
     *
     * @param player
     */
    void putPlayerUnit(Player player);

    /**
     * @param player
     * @return
     */
    PlayerUnit getPlayerUnit(Player player);

    /**
     * 移动
     * @param playerId
     * @param targetPos
     * @param mapId
     */
    void move(long playerId, Position targetPos, int mapId);

    /**
     * 移除副本信息
     *
     * @param accountId
     */
    void removeCopyScene(String accountId);

    /**
     * 实际做移动的地方
     * @param accountId
     * @param targetPos
     * @param mapId
     */
    void doMove(Player accountId, Position targetPos, int mapId);

    MapResource getMapResource(int mapId);

    void showMap(int mapId, String accountId);

    /**
     * 服务器启动时初始化场景
     */
    void init();

    /**
     * 做玩家升级
     */
    void doPlayerUpLevelSync(Player player);

    /**
     * 切换地图
     * @param accountId
     * @param mapId
     */
    void changeMap(String accountId, int mapId, boolean clientRequest);

    /**
     * 实际做切换地图的地方
     * @param player
     * @param targetScene
     * @param clientRequest
     */
    void doChangeMap(Player player, AbstractScene targetScene, boolean clientRequest);

    /**
     * 做登出时场景相关操作
     * @param accountId
     */
    void doLogoutBefore(String accountId);

    /**
     * 做离开地图的操作
     * @param accountId
     * @param targetScene
     * @param clientRequest
     */
    void leaveMap(String accountId, AbstractScene targetScene, boolean clientRequest);

    /**
     * 获取地图信息
     * @param mapId
     * @return
     */
    AbstractScene getScene(int mapId, String accountId);

    /**
     * 查看怪物信息
     *
     * @param mapId
     * @param monsterObjectId
     */
    void showObjectInfo(String accountId, int mapId, ObjectType objectType, long monsterObjectId);

    /**
     * 获取场景manager
     *
     * @return
     */
    ScenceManger getScenceMangaer();
}
