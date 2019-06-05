package com.game.scence.service;


import com.game.scence.constant.SceneType;
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
    void enterInitMap(TSession session, String accountId);

    /**
     *
     * @param session
     * @param mapId
     */
    void enterMap(TSession session, int mapId);
}
