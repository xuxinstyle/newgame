package com.game.scence.visible.packet;

import com.game.scence.visible.model.Position;

import java.util.List;
import java.util.Map;

/**
 * 返回场景里所以可视物的位置信息
 * @Author：xuxin
 * @Date: 2019/6/11 21:16
 */
public class SM_ScenePositionVisible {

    /**
     * 可视物位置信息<可视物类型id, 位置>
     */
    private Map<Integer ,List<Position>> positionMap;

    public Map<Integer, List<Position>> getPositionMap() {
        return positionMap;
    }

    public void setPositionMap(Map<Integer, List<Position>> positionMap) {
        this.positionMap = positionMap;
    }
}
