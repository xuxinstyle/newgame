package com.game.scence.monster.resource;

import com.game.base.attribute.Attribute;
import com.game.scence.drop.model.RandDrop;
import com.game.util.AttributeAnalyzeUtil;
import com.resource.anno.Analyze;
import com.resource.anno.LoadResource;

import java.util.List;

/**
 * @Author：xuxin
 * @Date: 2019/7/3 15:31
 */
@LoadResource
public class MonsterResource {
    /**
     * 标识id
     */
    private int id;
    /**
     * 怪物数量
     */
    private int monsterNum;
    /**
     * 怪物x坐标
     */
    private int px;
    /**
     * 怪物y坐标
     */
    private int py;

    /**
     * 怪物名字
     */
    private String name;
    /**
     * 怪物属性
     */
    private String attrStr;
    /**
     * 怪物属性
     */
    @Analyze("analyzeAttribute")
    private List<Attribute> attrs;
    /**
     * 怪物随机掉落物，TODO:需要解析
     */

    private String dropStr;
    /**
     * 怪物死后玩家固定增加的经验
     */
    private int exp;
    /**
     * 怪物的复活cd
     */
    private long reviveCd;
    /**
     * 道具列表
     */
    @Analyze("analyzeDrop")
    private RandDrop randDrop;

    /**
     * 掉落物掉落概率
     */
    private int prop;
    /**
     * 技能id
     */
    private int[] skillIds;

    public void analyzeDrop() {
        if (dropStr == null) {
            return;
        }
        randDrop = RandDrop.analyzeDrop(dropStr);
    }

    public void analyzeAttribute(){
        this.attrs = AttributeAnalyzeUtil.analyzeAttr(attrStr);
    }

    public int[] getSkillIds() {
        return skillIds;
    }

    public void setSkillIds(int[] skillIds) {
        this.skillIds = skillIds;
    }

    public int getProp() {
        return prop;
    }

    public void setProp(int prop) {
        this.prop = prop;
    }

    public long getReviveCd() {
        return reviveCd;
    }

    public void setReviveCd(long reviveCd) {
        this.reviveCd = reviveCd;
    }

    public int getPx() {
        return px;
    }

    public void setPx(int px) {
        this.px = px;
    }

    public int getPy() {
        return py;
    }

    public void setPy(int py) {
        this.py = py;
    }

    public int getMonsterNum() {
        return monsterNum;
    }

    public void setMonsterNum(int monsterNum) {
        this.monsterNum = monsterNum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAttrStr() {
        return attrStr;
    }

    public void setAttrStr(String attrStr) {
        this.attrStr = attrStr;
    }

    public List<Attribute> getAttrs() {
        return attrs;
    }

    public void setAttrs(List<Attribute> attrs) {
        this.attrs = attrs;
    }

    public String getDropStr() {
        return dropStr;
    }

    public void setDropStr(String dropStr) {
        this.dropStr = dropStr;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public RandDrop getRandDrop() {
        return randDrop;
    }

    public void setRandDrop(RandDrop randDrop) {
        this.randDrop = randDrop;
    }
}
