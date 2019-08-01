package com.game.scence.fight.command;

import com.game.SpringContext;
import com.game.base.attribute.Attribute;
import com.game.base.attribute.attributeid.AttributeId;
import com.game.base.attribute.container.CreatureAttributeContainer;
import com.game.base.executor.scene.impl.AbstractSceneCommand;
import com.game.base.gameobject.constant.ObjectType;
import com.game.role.player.model.Player;
import com.game.scence.base.model.AbstractScene;
import com.game.scence.fight.model.CreatureUnit;

import java.util.List;

/**
 * @Author：xuxin
 * @Date: 2019/7/15 9:25
 */
public class AddAttributeBuffSynCommand extends AbstractSceneCommand {
    /**
     * 添加的属性id
     */
    private AttributeId attrId;
    /**
     * 属性列表
     */
    private List<Attribute> attrs;

    private Player player;

    public static AddAttributeBuffSynCommand valueOf(Player player, AttributeId attrId, List<Attribute> attrs) {
        AddAttributeBuffSynCommand command = new AddAttributeBuffSynCommand(player.getCurrMapId(), player.getCurrSceneId(), player.getAccountId());
        command.setAttrId(attrId);
        command.setAttrs(attrs);
        command.setPlayer(player);
        return command;
    }

    public AddAttributeBuffSynCommand(int mapId, int sceneId, String accountId) {
        super(mapId, sceneId, accountId);
    }

    @Override
    public String getName() {
        return "AddAttributeBuffSynCommand";
    }

    @Override
    public void active() {
        AbstractScene scene = SpringContext.getScenceSerivce().getScene(getMapId(), player.getAccountId());
        CreatureUnit unit = scene.getUnit(ObjectType.PLAYER, player.getObjectId());
        CreatureAttributeContainer attributeContainerUnit = unit.getAttributeContainer();
        attributeContainerUnit.putAndComputeAttributes(attrId, attrs);

    }

    public AttributeId getAttrId() {
        return attrId;
    }

    public void setAttrId(AttributeId attrId) {
        this.attrId = attrId;
    }

    public List<Attribute> getAttrs() {
        return attrs;
    }

    public void setAttrs(List<Attribute> attrs) {
        this.attrs = attrs;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
