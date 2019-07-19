package com.game.scence.visible.service;


import com.game.base.gameobject.constant.ObjectType;
import com.game.role.player.model.Player;
import com.game.scence.base.model.AbstractScene;
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
    void enterMap(TSession session, String accountId, int mapId);

    /**
     *  @param session
     * @param player
     * @param mapId
     */
    void doEnterMap(Player player, int mapId);

    /**
     * 查看地图中所有的账号简略信息
     * @param session
     * @param mapId
     */
    void showAllVisibleInfo(TSession session, int mapId);

    /**
     * 查看场景中所有的玩家信息
     * @param session
     * @param accountId
     * @param mapId
     */

    /*void showAccountIdInfo(TSession session, int mapId, long objectId);*/

    /**
     * 处理移动请求
     * @param session
     * @param x
     * @param y
     * @param playerId
     */
    void move(long playerId, Position targetPos, int mapId);

    /**
     * 实际做移动的地方
     * @param accountId
     * @param targetPos
     * @param mapId
     */
    void doMove(long accountId, Position targetPos, int mapId);

    MapResource getMapResource(int mapId);

    void showMap(int mapId);

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
    void changeMap(String accountId,int mapId,boolean clientRequest);

    /**
     * 实际做切换地图的地方
     * @param player
     * @param targetMapId
     */
    void doChangeMap(Player player,int targetMapId);

    /**
     * 做登出时场景相关操作
     * @param accountId
     */
    void doLogoutBefore(String accountId);

    /**
     * 做离开地图的操作
     * @param accountId
     */
    void leaveMap(String accountId);

    /**
     * 获取地图信息
     * @param mapId
     * @return
     */
    AbstractScene getScene(int mapId);

    /**
     * 查看怪物信息
     *
     * @param mapId
     * @param monsterObjectId
     */
    void showObjectInfo(String accountId, int mapId, ObjectType objectType, long monsterObjectId);
}
