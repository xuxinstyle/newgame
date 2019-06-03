package com.game.scence.service;


import com.game.scence.constant.SceneType;
import com.socket.core.session.TSession;

/**
 * @Author：xuxin
 * @Date: 2019/6/3 10:29
 */
public interface ScenceService {
    void enterMap(TSession session, String accountId, SceneType mapId);
}
