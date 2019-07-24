package com.game.scence.monster.service;

import com.game.SpringContext;
import com.game.base.gameobject.constant.ObjectType;
import com.game.common.exception.RequestException;
import com.game.role.player.model.Player;
import com.game.scence.base.model.FieldFightScene;
import com.game.scence.fight.model.MonsterUnit;
import com.game.scence.monster.resource.MonsterResource;
import com.game.scence.visible.constant.MapType;
import com.game.scence.visible.model.MonsterDef;
import com.game.scence.visible.model.Position;
import com.game.scence.visible.resource.MapResource;
import com.game.util.SendPacketUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/7/3 17:13
 */
@Component
public class MonsterServiceImpl implements MonsterService {
    private static Logger logger = LoggerFactory.getLogger(MonsterServiceImpl.class);
    @Autowired
    private MonsterManager monsterManager;
    @Override
    public Map<Long, MonsterUnit> getMonsterUnits(int mapId) {
        MapResource mapResource = SpringContext.getScenceSerivce().getMapResource(mapId);
        List<MonsterDef> monsterList = mapResource.getMonsterList();
        Map<Long, MonsterUnit> monsterUnitMap = new HashMap<>();
        for (MonsterDef def : monsterList) {
            MonsterResource monsterResource = SpringContext.getMonsterService().getMonsterResource(def.getMonsterId());
            for (int i = 0; i < def.getNum(); i++) {
                MonsterUnit monsterUnit = MonsterUnit.valueOf(monsterResource);
                monsterUnit.setId(SpringContext.getIdentifyService().getNextIdentify(ObjectType.MONSTER));
                monsterUnit.setMapId(mapId);
                monsterUnit.setPosition(Position.valueOf(def.getX(), def.getY()));
                if (!checkPosition(monsterUnit.getPosition(), mapResource)) {
                    Position newInitPosition = Position.randomPosition(mapId);
                    monsterUnit.setPosition(newInitPosition);
                }
                monsterUnitMap.put(monsterUnit.getId(), monsterUnit);
            }
        }
        return monsterUnitMap;
    }


    private boolean checkPosition(Position initPos, MapResource mapResource) {
        int[][] mapcontext = mapResource.getMapcontext();
        int upY = mapcontext.length;
        int upX = mapcontext[0].length;
        int x = initPos.getX();
        int y = initPos.getY();
        if (x >= upX || y >= upY || x < 0 || y < 0 || mapcontext[x][y] != 0) {
            return false;
        }
        return true;
    }
    @Override
    public MonsterResource getMonsterResource(int monsterId) {

        return monsterManager.getMonsterResource(monsterId);
    }

    @Override
    public void fieldMonsterAttack(String accountId) {
        Player player = SpringContext.getPlayerSerivce().getPlayer(accountId);
        FieldFightScene scene = (FieldFightScene) SpringContext.getScenceSerivce().getScene(player.getCurrMapId(), accountId);
        Map<Long, MonsterUnit> monsterUnits = scene.getMonsterUnits();
        for (MonsterUnit monsterUnit : monsterUnits.values()) {
            try {
                monsterUnit.doAttackAfter(accountId, monsterUnit.getAttacker());
            } catch (RequestException e) {
                logger.warn("怪物[{}][{}]反击失败,原因[{}]", monsterUnit.getId(), monsterUnit.getVisibleName(), e.getErrorCode());
                SendPacketUtil.send(accountId, e.getErrorCode());

            }
        }
    }

}
