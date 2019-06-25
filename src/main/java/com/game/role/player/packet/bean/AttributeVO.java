package com.game.role.player.packet.bean;

/**
 * @Author：xuxin
 * @Date: 2019/6/17 11:43
 */
public class AttributeVO {
    /**
     * 攻击
     */
    private long attack;
    /**
     * 防御
     */
    private long defense;
    /**
     * 最大血量
     */
    private long maxHp;
    /**
     * 最大蓝量
     */
    private long maxMp;
    /**
     * 攻速
     */
    private long attackSpeed;
    /**
     * 力量
     */
    private long power;
    /**
     * 智力
     */
    private long intelltgence;
    /**
     * 体力
     */
    private long physical;
    /**
     * 敏捷
     */
    private long agile;

    public long getAttack() {
        return attack;
    }

    public void setAttack(long attack) {
        this.attack = attack;
    }

    public long getDefense() {
        return defense;
    }

    public void setDefense(long defense) {
        this.defense = defense;
    }

    public long getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(long maxHp) {
        this.maxHp = maxHp;
    }

    public long getMaxMp() {
        return maxMp;
    }

    public void setMaxMp(long maxMp) {
        this.maxMp = maxMp;
    }

    public long getAttackSpeed() {
        return attackSpeed;
    }

    public void setAttackSpeed(long attackSpeed) {
        this.attackSpeed = attackSpeed;
    }

    public long getPower() {
        return power;
    }

    public void setPower(long power) {
        this.power = power;
    }

    public long getIntelltgence() {
        return intelltgence;
    }

    public void setIntelltgence(long intelltgence) {
        this.intelltgence = intelltgence;
    }

    public long getPhysical() {
        return physical;
    }

    public void setPhysical(long physical) {
        this.physical = physical;
    }

    public long getAgile() {
        return agile;
    }

    public void setAgile(long agile) {
        this.agile = agile;
    }

    @Override
    public String toString() {
        return "AttributeVO{" +
                "attack=" + attack +
                ", defense=" + defense +
                ", maxHp=" + maxHp +
                ", maxMp=" + maxMp +
                ", attackSpeed=" + attackSpeed +
                ", power=" + power +
                ", intelltgence=" + intelltgence +
                ", physical=" + physical +
                ", agile=" + agile +
                '}';
    }
}
