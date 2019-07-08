package com.game.scence.field.service;

import com.game.SpringContext;
import com.game.scence.base.model.AbstractScene;
import com.game.scence.field.model.FieldFightScene;
import com.game.scence.field.packet.SM_ShowMonsterInfo;
import com.game.scence.fight.model.MonsterUnit;
import com.game.scence.visible.constant.MapType;
import com.game.util.SendPacketUtil;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/7/4 9:53
 */
@Component
public class FieldServiceImpl implements FieldService {

    @Override
    public void showMonsterInfo(String accountId,int mapId, long monsterObjectId) {
        if(mapId!=MapType.FIELD.getMapId()){
            return;
        }
        FieldFightScene fieldFightScene = (FieldFightScene)SpringContext.getScenceSerivce().getScene(mapId);
        Map<Long, MonsterUnit> monsterUnits = fieldFightScene.getMonsterUnits();
        if(monsterUnits==null){
            return;
        }
        MonsterUnit monsterUnit = monsterUnits.get(monsterObjectId);
        SendPacketUtil.send(accountId,SM_ShowMonsterInfo.valueOf(monsterUnit));
    }

}
