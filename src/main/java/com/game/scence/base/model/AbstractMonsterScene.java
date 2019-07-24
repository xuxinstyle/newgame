package com.game.scence.base.model;

import com.game.scence.fight.model.MonsterUnit;
import com.game.scence.visible.model.Position;
import com.game.scence.visible.packet.bean.VisibleVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/7/23 17:21
 */
public abstract class AbstractMonsterScene extends AbstractScene {
    /**
     * 怪物战斗单元
     */
    private Map<Long, MonsterUnit> monsterUnits = new HashMap<>();

    @Override
    public Map<Integer, List<Position>> getVisiblePosition() {
        Map<Integer, List<Position>> positionMap = super.getVisiblePosition();

        for (MonsterUnit monsterUnit : monsterUnits.values()) {
            List<Position> positions = positionMap.get(monsterUnit.getType().getTypeId());
            if (positions == null) {
                positions = new ArrayList<>();
                positionMap.put(monsterUnit.getType().getTypeId(), positions);
            }
            positions.add(monsterUnit.getPosition());
        }
        return positionMap;
    }

    @Override
    public List<String> getAccountIds() {
        return super.getAccountIds();
    }

    public Map<Long, MonsterUnit> getMonsterUnits() {
        return monsterUnits;
    }

    public void setMonsterUnits(Map<Long, MonsterUnit> monsterUnits) {
        this.monsterUnits = monsterUnits;
    }

    @Override
    public List<VisibleVO> getVisibleVOList() {
        /**
         * 玩家可是信息
         */
        List<VisibleVO> visibleVOList = super.getVisibleVOList();
        /**
         * 怪物的可视信息
         */
        for (MonsterUnit monsterUnit : getMonsterUnits().values()) {
            if (monsterUnit.isDead()) {
                continue;
            }
            VisibleVO visibleVO = new VisibleVO();
            visibleVO.setVisibleName(monsterUnit.getVisibleName());
            visibleVO.setPosition(monsterUnit.getPosition());
            visibleVO.setType(monsterUnit.getType());
            visibleVO.setObjectId(monsterUnit.getId());
            visibleVO.setCurrHp(monsterUnit.getCurrHp());
            visibleVO.setCurrMp(monsterUnit.getCurrMp());
            visibleVO.setMaxHp(monsterUnit.getMaxHp());
            visibleVO.setMaxMp(monsterUnit.getMaxMp());
            visibleVOList.add(visibleVO);
        }
        return visibleVOList;
    }
}
