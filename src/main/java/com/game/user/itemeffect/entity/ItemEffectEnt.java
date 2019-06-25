package com.game.user.itemeffect.entity;

import com.db.AbstractEntity;
import com.game.user.itemeffect.model.ItemEffectInfo;
import com.socket.utils.JsonUtils;
import org.hibernate.annotations.Table;

import javax.persistence.*;

/**
 * @Author：xuxin
 * @Date: 2019/6/21 11:12
 */
@Entity(name = "item_effect")
@Table(appliesTo = "item_effect", comment = "道具时效")
public class ItemEffectEnt extends AbstractEntity<Long> {
    /**
     * 角色id
     */
    @Id
    @Column(columnDefinition = "bigint default 10000 comment '角色id'", nullable = false)
    private long playerId;

    @Lob
    @Column(columnDefinition = "blob comment '道具时效数据'", nullable = false)
    private volatile byte[] itemEffectData;

    @Transient
    private ItemEffectInfo itemEffectInfo;

    public static ItemEffectEnt valueOf(long playerId){
        ItemEffectEnt itemEffectEnt = new ItemEffectEnt();
        itemEffectEnt.setPlayerId(playerId);
        itemEffectEnt.setItemEffectInfo(ItemEffectInfo.valueOf());
        return itemEffectEnt;
    }
    @Override
    public void doSerialize() {
        this.itemEffectData =JsonUtils.object2Bytes(itemEffectInfo);
    }

    @Override
    public void doDeserialize() {
        this.itemEffectInfo = JsonUtils.bytes2Object(this.itemEffectData, ItemEffectInfo.class);
    }

    @Override
    public Long getId() {
        return playerId;
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public ItemEffectInfo getItemEffectInfo() {
        return itemEffectInfo;
    }

    public void setItemEffectInfo(ItemEffectInfo itemEffectInfo) {
        this.itemEffectInfo = itemEffectInfo;
    }
}
