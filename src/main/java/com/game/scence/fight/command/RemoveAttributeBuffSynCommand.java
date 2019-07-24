package com.game.scence.fight.command;

import com.game.SpringContext;
import com.game.base.attribute.attributeid.AttributeId;
import com.game.base.attribute.container.CreatureAttributeContainer;
import com.game.base.executor.scene.impl.AbstractSceneCommand;
import com.game.base.gameobject.constant.ObjectType;
import com.game.role.player.model.Player;
import com.game.scence.base.model.AbstractScene;
import com.game.scence.fight.model.CreatureUnit;

/**
 * @Author：xuxin
 * @Date: 2019/7/15 9:50
 */
public class RemoveAttributeBuffSynCommand extends AbstractSceneCommand {

    /**
     * 添加的属性id
     */
    private AttributeId attrId;
    /**
     * 玩家战斗单元
     */
    private Player player;

    public static RemoveAttributeBuffSynCommand valueOf(Player player, AttributeId attrId) {
        RemoveAttributeBuffSynCommand command = new RemoveAttributeBuffSynCommand(player.getCurrMapId(), player.getCurrSceneId(), player.getAccountId());
        command.setAttrId(attrId);
        command.setPlayer(player);
        return command;
    }

    public RemoveAttributeBuffSynCommand(int mapId, int sceneId, String accountId) {
        super(mapId, sceneId, accountId);
    }

    public AttributeId getAttrId() {
        return attrId;
    }

    public void setAttrId(AttributeId attrId) {
        this.attrId = attrId;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public String getName() {
        return "RemoveAttributeBuffSynCommand";
    }

    @Override
    public void active() {
        AbstractScene scene = SpringContext.getScenceSerivce().getScene(player.getCurrMapId(), player.getAccountId());
        CreatureUnit unit = scene.getUnit(ObjectType.PLAYER, player.getObjectId());
        CreatureAttributeContainer attributeContainer = unit.getAttributeContainer();
        attributeContainer.removeAndCompteAttribtues(attrId);
        if (unit.getCurrHp() > unit.getMaxHp()) {
            unit.setCurrHp(unit.getMaxHp());
        }
        if (unit.getCurrMp() > unit.getMaxMp()) {
            unit.setCurrMp(unit.getMaxMp());
        }
    }
}
