package com.game.user.item.model;

import com.game.SpringContext;
import com.game.base.gameObject.GameObject;
import com.game.base.gameObject.constant.ObjectType;
import com.game.role.player.model.Player;
import com.game.user.item.constant.ItemType;
import com.game.user.item.resource.ItemResource;
import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonTypeInfo;


import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/6/12 16:59
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS,include=JsonTypeInfo.As.PROPERTY,property="@class")
@JsonSubTypes({@JsonSubTypes.Type(value=Equipment.class),
            @JsonSubTypes.Type(value=Exp.class)})
public abstract class AbstractItem extends GameObject implements Comparable<AbstractItem> {
    /**
     * 配置表中的唯一Id
     */
    protected int itemModelId;
    /**
     * 数量
     */
    protected int num;
    /**
     * 物品状态
     */
    protected int status;
    /**
     * 过期时间
     */
    protected long deprecatedTime;

    /**
     * 道具类型
     */
    protected int itemType;

    public AbstractItem() {

    }

    public AbstractItem(AbstractItem item) {
        this.objectId = createItemObjectId();
        this.itemModelId = item.getItemModelId();
        this.num = item.getNum();
        this.status = item.getStatus();
        this.deprecatedTime = item.getDeprecatedTime();
        this.itemType = item.getItemType();
    }
    public void addItem(AbstractItem item) {
        this.itemModelId = item.getItemModelId();
        this.num = item.getNum();
        this.status = item.getStatus();
        this.deprecatedTime = item.getDeprecatedTime();
    }
    public AbstractItem copy(){

        return this;
    }
    public boolean reduceNum(int num){
        if(this.num-num<0){
            return false;
        }
        this.num -= num;
        return true;
    }
    public boolean addNumAndCheck(int num) {
        if (this.num + num > getOverLimit()) {
            return false;
        }
        this.num += num;
        return true;
    }

    public ItemResource getResource() {
        return SpringContext.getItemService().getItemResource(itemModelId);
    }

    public int getOverLimit() {
        return getResource().getOverLimit();
    }

    /**
     * 根据配置资源获取
     */
    public void init(ItemResource itemResource, Map<String, Object> params) {
        this.itemModelId = itemResource.getId();
        this.itemType = ItemType.valueOf(itemResource.getItemType()).getId();

    }

    public void use(String accountId){

    }
    public long createItemObjectId() {
        return SpringContext.getIdentifyService().getNextIdentify(ObjectType.ITEM);
    }

    public int getItemModelId() {
        return itemModelId;
    }

    public void setItemModelId(int itemModelId) {
        this.itemModelId = itemModelId;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getDeprecatedTime() {
        return deprecatedTime;
    }

    public void setDeprecatedTime(long deprecatedTime) {
        this.deprecatedTime = deprecatedTime;
    }

    @Override
    public String getName() {
        return getResource().getName();
    }

    public int getJob(){
        return getResource().getJobLimit();
    }
    // TODO:根据道具的品质排序，对比，品质高的放在最前面
    @Override
    public int compareTo(AbstractItem o) {
        return 0;
    }

}
