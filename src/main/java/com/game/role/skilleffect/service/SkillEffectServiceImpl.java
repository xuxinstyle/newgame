package com.game.role.skilleffect.service;

import com.game.SpringContext;
import com.game.base.attribute.attributeid.AttributeId;
import com.game.base.attribute.attributeid.MedicineAttributeId;
import com.game.base.attribute.attributeid.SkillEffectAttributeId;
import com.game.base.executor.ICommand;
import com.game.base.gameobject.constant.ObjectType;
import com.game.role.skilleffect.command.ShowEffectCommand;
import com.game.role.skilleffect.constant.BuffType;
import com.game.role.skilleffect.model.AbstractSkillEffect;
import com.game.role.skilleffect.packet.SM_ShowUnitEffect;
import com.game.role.skilleffect.resource.SkillEffectResource;
import com.game.scence.base.model.AbstractScene;
import com.game.scence.fight.model.CreatureUnit;
import com.game.user.itemeffect.command.ItemExpireDelayCommand;
import com.game.util.SendPacketUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/7/11 21:26
 */
@Component
public class SkillEffectServiceImpl implements SkillEffectService {
    @Autowired
    private SkillEffectManager skillEffectManager;

    @Override
    public SkillEffectResource getSkillEffectResource(int id) {
        return skillEffectManager.getSkillEffectResource(id);
    }

    @Override
    public AbstractSkillEffect getSkillEffect(int effectId) {
        return skillEffectManager.getSkillEffect(effectId);
    }

    @Override
    public Collection<SkillEffectResource> getSkillEffectResourceAll() {
        return skillEffectManager.getSkillEffectResourceAll();
    }

    //在场景线程中执行
    @Override
    public void showUnitEffect(String accountId, int mapId, ObjectType targetType, long targetId) {

        AbstractScene scene = SpringContext.getScenceSerivce().getScene(mapId, accountId);
        CreatureUnit unit = scene.getUnit(targetType, targetId);
        Map<AttributeId, ICommand> buffCommandMap = unit.getBuffCommandMap();
        for (Map.Entry<AttributeId, ICommand> entry : buffCommandMap.entrySet()) {
            AttributeId key = entry.getKey();
            SM_ShowUnitEffect sm = SM_ShowUnitEffect.valueOf(key.getName());
            SendPacketUtil.send(accountId, sm);
        }
    }

    @Override
    public void showEffect(String accountId, int mapId, ObjectType targetType, long targetId) {
        Map<Integer, ItemExpireDelayCommand> itemExpireDelayCommandMap = SpringContext.getItemEffectService().getItemExpireDelayCommandMap(targetId);
        if (itemExpireDelayCommandMap != null) {
            for (Map.Entry<Integer, ItemExpireDelayCommand> entry : itemExpireDelayCommandMap.entrySet()) {
                SM_ShowUnitEffect sm = SM_ShowUnitEffect.valueOf(new MedicineAttributeId(entry.getKey()).getName());
                SendPacketUtil.send(accountId, sm);
            }
        }

        ShowEffectCommand command = ShowEffectCommand.valueOf(accountId, mapId, targetType, targetId);
        SpringContext.getSceneExecutorService().submit(command);
    }


}
