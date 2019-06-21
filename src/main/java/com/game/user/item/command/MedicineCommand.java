package com.game.user.item.command;

import com.game.SpringContext;
import com.game.base.attribute.Attribute;
import com.game.base.attribute.AttributeContainer;
import com.game.base.executor.account.Impl.AbstractAccountCommand;
import com.game.role.player.entity.PlayerEnt;
import com.game.role.player.model.Player;
import com.game.user.item.model.MedicineEffect;
import com.game.user.item.model.UseEffect;
import com.game.user.item.resource.ItemResource;

import java.util.List;

/**
 * @Author：xuxin
 * @Date: 2019/6/20 21:47
 */
public class MedicineCommand extends AbstractAccountCommand {
    /**
     * 药品道具id
     */
    private int itemModelId;
    public MedicineCommand(String accountId, int itemModelId) {
        super(accountId);
        this.itemModelId = itemModelId;
    }

    @Override
    public String getName() {
        return "MedicineCommand";
    }

    @Override
    public void active() {
        ItemResource itemResource = SpringContext.getItemService().getItemResource(itemModelId);
        MedicineEffect useEffect = (MedicineEffect)itemResource.getUseEffect();
        List<Attribute> addAttributeList = useEffect.getAddAttributeList();
        PlayerEnt playerEnt = SpringContext.getPlayerSerivce().getPlayerEnt(getAccountId());
        AttributeContainer<Player> attributeContainer = playerEnt.getPlayer().getAttributeContainer();
        attributeContainer.removeAndCompute(addAttributeList);
        SpringContext.getPlayerSerivce().save(playerEnt);
    }

    public int getItemModelId() {
        return itemModelId;
    }

    public void setItemModelId(int itemModelId) {
        this.itemModelId = itemModelId;
    }
}
