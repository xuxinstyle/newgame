package com.game.scence.service;


import com.game.scence.resource.MapResource;
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
     *
     * @param session
     * @param mapId
     */
    void doEnterMap(TSession session,String accountId, int mapId);

    /**
     * 移除场景中的账号信息
     * @param mapdId
     * @param accountId
     */
    void removeScenceAccountId(int mapdId, String accountId);

    /**
     * 查看地图中所有的账号简略信息
     * @param session
     * @param mapId
     */
    void showAllAccountInfo(TSession session, int mapId);

    /**
     * 查看场景中所有的玩家信息
     * @param session
     * @param accountId
     * @param mapId
     */

    void showAccountInfo(TSession session, String accountId, int mapId);

    /**
     * 将玩家信息放到场景中
     * @param mapId
     * @param accountId
     */
    void setScenceAccountId(int mapId, String accountId);

    /**
     * 处理移动请求
     * @param session
     * @param x
     * @param y
     */
    void move(TSession session, int x, int y);

    MapResource getMapResource(int mapId);

    void showMap(int mapId);
    void init();

}
