package com.game.scence.monster.resource;

import com.game.scence.visible.model.MonsterDef;
import com.resource.anno.LoadResource;

/**
 * @Author：xuxin
 * @Date: 2019/8/1 10:11
 */
@LoadResource
public class MonsterGroupResource {
    /**
     * 怪物组id
     */
    private int id;
    /**
     * 怪物内容
     */
    private MonsterDef[] monsters;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MonsterDef[] getMonsters() {
        return monsters;
    }

    public void setMonsters(MonsterDef[] monsters) {
        this.monsters = monsters;
    }

}
