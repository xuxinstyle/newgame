package com.game.scence.service;


import com.game.scence.constant.SceneType;
import com.socket.core.session.TSession;

import java.util.List;

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
    void enterInitMap(TSession session, String accountId);

    /**
     *
     * @param session
     * @param mapId
     */
    void enterMap(TSession session, int mapId);

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

    void showAccountInfo(TSession session, String accountId, int mapId);

}
