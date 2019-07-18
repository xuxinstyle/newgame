package com.game.scence.visible.resource;

import com.game.base.gameobject.constant.ObjectType;
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
    /** 这里的属性必须为public的权限*/
    /** 地图id*/
    public int id;
    /**
     * 地图名称
     */
    private String mapName;

    public int initX;

    public int initY;
    /**
     * 复活cd
     */
    private long reviveCd;
    /** 地图实际形状*/
    public String context;

    @Analyze("analyzeMap")
    private int[][] mapcontext;
    /**
     * 可攻击的目标类型目标
     */
    private String targetStr;
    @Analyze("analyzetargetTypes")
    private List<ObjectType> targetTypes;
    /**
     * 地图中的怪物
     */
    private String monsters;

    @Analyze("analyzeMonster")
    private List<Integer> monsterList;

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


    public void analyzeMonster(){
        if(monsters==null){
            return;
        }
        String[] split = monsters.split(",");
        for(String str:split){
            int monsterId = Integer.parseInt(str);
            if(monsterList==null){
                monsterList = new ArrayList<>();
            }
            monsterList.add(monsterId);
        }
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

    public String getMonsters() {
        return monsters;
    }

    public void setMonsters(String monsters) {
        this.monsters = monsters;
    }

    public List<Integer> getMonsterList() {
        return monsterList;
    }

    public void setMonsterList(List<Integer> monsterList) {
        this.monsterList = monsterList;
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
