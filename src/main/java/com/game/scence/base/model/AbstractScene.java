package com.game.scence.base.model;

import com.game.role.player.model.Player;
import com.game.scence.visible.model.Position;
import com.game.scence.visible.packet.bean.VisibleVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/7/4 21:10
 */
public abstract class AbstractScene {
    /**
     * 地图id
     */
    private int mapId;
    /**
     * 场景id
     */
    private int sceneId;
    /**
     * 玩家账号id  FIXME:用来客户端显示用也可以用来做玩家是否在这个地图的判断
     */
    private List<String> accountIds;
    /**
     * 获取可是对象的信息
     */
    public abstract List<VisibleVO> getVisibleVOList();
    /**
     * FIXME:只是用来客户端显示
     * @return
     */
    public abstract Map<Integer, List<Position>> getVisiblePosition();

    /**
     * 移动
     * @param accountId
     * @param targetpos
     */
    public abstract void move(String accountId,Position targetpos);


    public void enter(Player player){
        if(accountIds==null){
            accountIds = new ArrayList<>();
        }
        accountIds.add(player.getAccountId());
        player.setChangeMapId(false);
    }
    public void leave(String accountId){
        accountIds.remove(accountId);
    }
    public List<String> getAccountIds() {
        return accountIds;
    }

    public void setAccountIds(List<String> accountIds) {
        this.accountIds = accountIds;
    }
    public AbstractScene(){

    }
    public AbstractScene(int mapId){
        this(mapId,0);
    }

    public AbstractScene(int mapId, int sceneId){
        this.mapId = mapId;
        this.sceneId = sceneId;
    }

    public abstract void init();

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public int getSceneId() {
        return sceneId;
    }

    public void setSceneId(int sceneId) {
        this.sceneId = sceneId;
    }
}
