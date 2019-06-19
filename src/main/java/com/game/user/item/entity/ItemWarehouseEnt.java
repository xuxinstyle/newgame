/*
package com.game.user.item.entity;

import com.db.AbstractEntity;
import com.game.user.item.model.ItemStorageInfo;
import com.socket.Utils.ProtoStuffUtil;
import org.hibernate.annotations.Table;

import javax.persistence.*;

*/
/**
 * @Author：xuxin
 * @Date: 2019/6/11 10:54
 *//*


@Entity(name = "item_storage")
@Table(appliesTo = "item_storage", comment = "背包")
public class ItemWarehouseEnt extends AbstractEntity<String> {
    */
/**
     * 账号Id
     *//*

    @Id
    @Column(columnDefinition = "varchar(255) character set utf8 collate utf8_bin comment '账号Id'",nullable = false)
    private String accountId;

    @Lob
    @Column(columnDefinition = "MediumBlob comment 背包数据")
    private volatile byte[] packData;

    @Transient
    private ItemStorageInfo pack;

    @Override
    public void doSerialize() {
        this.packData = ProtoStuffUtil.serializer(pack);
    }

    @Override
    public void doDeserialize() {
        ProtoStuffUtil.deserializer(this.packData,ItemStorageInfo.class);
    }

    @Override
    public String getId() {
        return accountId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public ItemStorageInfo getPack() {
        return pack;
    }

    public void setPack(ItemStorageInfo pack) {
        this.pack = pack;
    }
}
*/
