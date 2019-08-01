package com.game.scence.visible.resource;

import com.game.base.gameobject.constant.ObjectType;
import com.game.scence.visible.model.MonsterDef;
import com.game.scence.visible.model.Position;
import com.game.util.StringUtil;
import com.resource.anno.Analyze;
import com.resource.anno.LoadResource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author：xuxin
 * @Date: 2019/6/4 20:47
 */
@LoadResource
public class MapResource {

    /** 地图id*/
    private int id;
    /**
     * 地图名称
     */
    private String mapName;

    private int initX;

    private int initY;
    /**
     * 复活cd
     */
    private long reviveCd;
    /** 地图实际形状*/
    private String context;

    @Analyze("analyzeMap")
    private int[][] mapcontext;
    /**
     * 可攻击的目标类型目标
     */
    private String targetStr;

    @Analyze("analyzetargetTypes")
    private List<ObjectType> targetTypes;
    /**
     * 地图中的怪物组
     */
    private int[] monsters;


    /**
     * 开服时是否需要初始化
     */
    private boolean needToInit;
    /**
     * 副本时间
     */
    private long duration;
    /**
     * 首次通关奖励
     */
    private int firstReward;
    /**
     * 重复通关奖励
     */
    private int repeatReward;
    /**
     * 下一关
     */
    private int nextFloor;
    /**
     * 开启条件
     */
    private String openCondition;
    /**
     * 地图类型
     */
    private int mapType;

    public int getMapType() {
        return mapType;
    }

    public void setMapType(int mapType) {
        this.mapType = mapType;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public int getFirstReward() {
        return firstReward;
    }

    public void setFirstReward(int firstReward) {
        this.firstReward = firstReward;
    }

    public int getRepeatReward() {
        return repeatReward;
    }

    public void setRepeatReward(int repeatReward) {
        this.repeatReward = repeatReward;
    }

    public int getNextFloor() {
        return nextFloor;
    }

    public void setNextFloor(int nextFloor) {
        this.nextFloor = nextFloor;
    }

    public String getOpenCondition() {
        return openCondition;
    }

    public void setOpenCondition(String openCondition) {
        this.openCondition = openCondition;
    }

    public String getTargetStr() {
        return targetStr;
    }

    public void setTargetStr(String targetStr) {
        this.targetStr = targetStr;
    }

    public List<ObjectType> getTargetTypes() {
        return targetTypes;
    }

    public void setTargetTypes(List<ObjectType> targetTypes) {
        this.targetTypes = targetTypes;
    }

    public void analyzetargetTypes() {
        if (targetStr == null) {
            return;
        }
        String[] split = targetStr.split(StringUtil.DOU_HAO);
        List<ObjectType> typeList = new ArrayList<>();
        for (String typestr : split) {
            typeList.add(ObjectType.valueOf(typestr));
        }
        targetTypes = typeList;
    }
    public void analyzeMap(){
        String[] mapY = this.context.split(",");
        if(mapY.length<=0) {
            return ;
        }
        String[] mapX = mapY[0].split(" ");
        int[][] mapcontext = new int[mapX.length][mapY.length];
        for (int i = 0; i < mapY.length; i++) {
            String[] mapj = mapY[i].split(" ");
            for(int j = 0; j < mapj.length ;j++){
                mapcontext[i][j] = Integer.parseInt(mapj[j]);
            }
        }
        this.mapcontext = mapcontext;
    }





    public boolean isNeedToInit() {
        return needToInit;
    }

    public void setNeedToInit(boolean needToInit) {
        this.needToInit = needToInit;
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public long getReviveCd() {
        return reviveCd;
    }

    public void setReviveCd(long reviveCd) {
        this.reviveCd = reviveCd;
    }

    public int[] getMonsters() {
        return monsters;
    }

    public void setMonsters(int[] monsters) {
        this.monsters = monsters;
    }

    public int[][] getMapcontext() {
        return this.mapcontext;
    }

    public void setMapcontext(int[][] mapcontext) {
        this.mapcontext = mapcontext;
    }

    public int getInitX() {
        return initX;
    }

    public void setInitX(int initX) {
        this.initX = initX;
    }

    public int getInitY() {
        return initY;
    }

    public void setInitY(int initY) {
        this.initY = initY;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
