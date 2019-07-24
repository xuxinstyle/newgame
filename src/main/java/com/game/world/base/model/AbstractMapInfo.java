package com.game.world.base.model;

import org.codehaus.jackson.annotate.JsonTypeInfo;

/**
 * @Author：xuxin
 * @Date: 2019/7/22 16:15
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public abstract class AbstractMapInfo {
    /**
     * 初始化玩家地图信息
     *
     * @return
     */
    public abstract AbstractMapInfo valueOf();

    /**
     * 判断地图是否通关
     *
     * @param mapId
     * @return
     */
    public abstract boolean isPass(int mapId);


    /**
     * 是否开启该地图
     *
     * @param mapId
     * @return
     */
    public abstract boolean isOpen(int mapId);

    /**
     * 开启下一关卡
     *
     * @param mapId
     */
    public abstract void openNext(int mapId);
}
