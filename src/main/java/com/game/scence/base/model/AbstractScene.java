package com.game.scence.base.model;

import com.game.base.executor.ICommand;
import com.game.role.player.model.Player;
import com.game.scence.fight.model.CreatureUnit;
import com.game.scence.fight.model.FightAccount;
import com.game.scence.visible.model.Position;
import com.game.scence.visible.packet.bean.VisibleVO;

import java.util.ArrayList;
import java.util.HashMap;
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
     * 场景战斗数据
     */
    private Map<String, FightAccount> fightAccounts;

    /**
     * 场景的定时任务??? FIXME:有什么用？ 用来管理command但是实际 我在项目中没有用到
     */


    public AbstractScene(){

    }
    public AbstractScene(int mapId){
        this(mapId,0);
    }

    public AbstractScene(int mapId, int sceneId){
        this.mapId = mapId;
        this.sceneId = sceneId;
    }
    /**
     * 获取可视对象的信息
     */
    public List<VisibleVO> getVisibleVOList(){
        /**
         * 玩家的可视信息
         */
        List<VisibleVO> visibleVOList = new ArrayList<>();
        Map<String, FightAccount> fightAccounts = getFightAccounts();
        for(FightAccount fightAccount:fightAccounts.values()){
            Map<Long, CreatureUnit> creatureUnitMap = fightAccount.getCreatureUnitMap();
            if(creatureUnitMap==null){
                continue ;
            }
            for(CreatureUnit creatureUnit:creatureUnitMap.values()) {
                VisibleVO visibleVO = new VisibleVO();
                visibleVO.setObjectId(creatureUnit.getId());
                visibleVO.setType(creatureUnit.getType());
                visibleVO.setPosition(creatureUnit.getPosition());
                visibleVO.setVisibleName(creatureUnit.getVisibleName());
                visibleVOList.add(visibleVO);
            }
        }
        return visibleVOList;
    }
    /**
     * FIXME:只是用来客户端显示
     * @return
     */
    public Map<Integer, List<Position>> getVisiblePosition(){
        Map<Integer, List<Position>> positionMap= new HashMap<>();
        Map<String, FightAccount> fightAccounts = getFightAccounts();
        for(FightAccount fightAccount:fightAccounts.values()){
            if(fightAccount==null){
                continue;
            }
            Map<Long, CreatureUnit> creatureUnitMap = fightAccount.getCreatureUnitMap();
            for(CreatureUnit creatureUnit:creatureUnitMap.values()){
                List<Position> positions = positionMap.get(creatureUnit.getType().getTypeId());
                if(positions==null){
                    positions =  new ArrayList<>();
                    positionMap.put(creatureUnit.getType().getTypeId(),positions);
                }
                positions.add(creatureUnit.getPosition());
            }
        }
        return positionMap;
    }
    /**
     * 用于客户端获取场景中的玩家信息
     * @return
     */
    public List<String> getAccountIds(){
        List<String> accountIds = new ArrayList<>();
        Map<String, FightAccount> fightAccounts = getFightAccounts();
        for(Map.Entry<String, FightAccount> entry:fightAccounts.entrySet()){
            accountIds.add(entry.getKey());
        }
        return accountIds;
    }
    /**
     * 移动
     * @param accountId
     * @param targetpos
     */
    public abstract void move(String accountId,Position targetpos);

    /**
     * 初始化地图
     */
    public abstract void init();

    /**
     * 是否可以释放技能
     */
    public boolean isCanUseSkill(){
        return false;
    }

    public void enter(Player player){
        fightAccounts.put(player.getAccountId(),FightAccount.valueOf(player));
    }

    public void leave(String accountId){
        FightAccount fightAccount = fightAccounts.get(accountId);
        for(CreatureUnit creatureUnit:fightAccount.getCreatureUnitMap().values()){
            creatureUnit.clearAllCommand();
        }
        fightAccounts.remove(accountId);
    }

    public Map<String, FightAccount> getFightAccounts() {
        return fightAccounts;
    }

    public void setFightAccounts(Map<String, FightAccount> fightAccounts) {
        this.fightAccounts = fightAccounts;
    }

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
