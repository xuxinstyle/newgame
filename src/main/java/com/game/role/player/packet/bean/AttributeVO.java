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
    private long max_hp;
    /**
     * 最大蓝量
     */
    private long max_mp;
    /**
     * 攻速
     */
    private long attack_speed;
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

    public long getMax_hp() {
        return max_hp;
    }

    public void setMax_hp(long max_hp) {
        this.max_hp = max_hp;
    }

    public long getMax_mp() {
        return max_mp;
    }

    public void setMax_mp(long max_mp) {
        this.max_mp = max_mp;
    }

    public long getAttack_speed() {
        return attack_speed;
    }

    public void setAttack_speed(long attack_speed) {
        this.attack_speed = attack_speed;
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
}
