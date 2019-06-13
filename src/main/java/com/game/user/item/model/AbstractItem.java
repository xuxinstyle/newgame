package com.game.user.item.model;

import com.game.SpringContext;
import com.game.base.gameObject.GameObject;
import com.game.base.gameObject.constant.ObjectType;
import com.game.user.item.resource.ItemResource;

import java.util.Map;

/**
 *
 * @Author：xuxin
 * @Date: 2019/6/12 16:59
 */
public abstract class AbstractItem extends GameObject implements Comparable<AbstractItem> {
    /**
     * 配置表中的唯一Id
     */
    protected int itemModelId;
    /** 数量*/
    protected int num;
    /** 物品状态*/
    protected int status;
    /** 过期时间*/
    protected long deprecatedTime;
    public AbstractItem(){

    }
    public AbstractItem(AbstractItem item){
        this.objectId =createItemObjectId();
        this.itemModelId = item.getItemModelId();
        this.num = item.getNum();
        this.status = item.getStatus();
        this.deprecatedTime = item.getDeprecatedTime();
    }
    /** 根据配置资源获取*/
    public void init(ItemResource itemResource, Map<String,Object> params){

    }
    private long createItemObjectId(){
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

    // TODO: 这里根据配置资源获取道具名
    @Override
    public String getName(){
        return null;
    }
    // TODO:根据道具的品质排序，对比，品质高的放在最前面
    @Override
    public int compareTo(AbstractItem o) {
        return 0;
    }

}
