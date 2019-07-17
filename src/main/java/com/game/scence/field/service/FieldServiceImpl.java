package com.game.scence.field.service;

import com.game.SpringContext;
import com.game.base.gameobject.constant.ObjectType;
import com.game.base.gameobject.model.Creature;
import com.game.scence.base.model.AbstractScene;
import com.game.scence.field.model.FieldFightScene;
import com.game.scence.field.packet.SM_ShowMonsterInfo;
import com.game.scence.fight.model.CreatureUnit;
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
        AbstractScene scene = SpringContext.getScenceSerivce().getScene(mapId);
        CreatureUnit unit = scene.getUnit(ObjectType.MONSTER, monsterObjectId);

        SendPacketUtil.send(accountId, SM_ShowMonsterInfo.valueOf(unit));
    }

}
