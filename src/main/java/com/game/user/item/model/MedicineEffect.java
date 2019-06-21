package com.game.user.item.model;

import com.game.SpringContext;
import com.game.base.attribute.Attribute;
import com.game.base.attribute.AttributeContainer;
import com.game.base.attribute.constant.AttributeType;
import com.game.role.player.entity.PlayerEnt;
import com.game.role.player.model.Player;
import com.game.user.item.command.MedicineDelayCommand;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author：xuxin
 * @Date: 2019/6/17 14:19
 */
public class MedicineEffect extends UseEffect {
    /**
     * 使用持续时间
     */
    private long useTime;
    /**
     * 增加的属性
     */
    private List<Attribute> addAttributeList = new ArrayList<>();
    /**
     * 道具表id
     */
    private int itemModelId;

    @Override
    public void init(String effect, Map<String , Object> param){
        String[] split = effect.split(":");
        for(String str:split[0].split(";")){
            if(str==null||str.equals("")){
                continue;
            }
            String[] attr = str.split(",");
            AttributeType attributeType = AttributeType.valueOf(attr[0]);
            Attribute attribute = Attribute.valueOf(attributeType, Long.parseLong(attr[1]));
            this.addAttributeList.add(attribute);
        }
        /**
         * 这个地方配置表中配置的是
         */
        useTime = Long.parseLong(split[1])*1000*60;
        Object itemModelId = param.get("itemModelId");
        this.itemModelId = (int)itemModelId;
    }

    /**
     * TODO:加在玩家身上
     */
    @Override
    public void use(String acountId,int num) {
        PlayerEnt playerEnt = SpringContext.getPlayerSerivce().getPlayerEnt(acountId);
        Player player = playerEnt.getPlayer();
        AttributeContainer<Player> attributeContainer = player.getAttributeContainer();
        attributeContainer.addAndComputeMap(addAttributeList);
        SpringContext.getPlayerSerivce().save(playerEnt);
        MedicineDelayCommand command = new MedicineDelayCommand(useTime,acountId,itemModelId);
        SpringContext.getCommonExecutorService().submit(command);
    }

    public List<Attribute> getAddAttributeList() {
        return addAttributeList;
    }

    public void setAddAttributeList(List<Attribute> addAttributeList) {
        this.addAttributeList = addAttributeList;
    }

    public long getUseTime() {
        return useTime;
    }

    public void setUseTime(long useTime) {
        this.useTime = useTime;
    }

    public int getItemModelId() {
        return itemModelId;
    }

    public void setItemModelId(int itemModelId) {
        this.itemModelId = itemModelId;
    }
}
