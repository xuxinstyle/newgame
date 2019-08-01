package com.game.world.hopetower.packet;

/**
 * @Author：xuxin
 * @Date: 2019/8/1 10:46
 */
public class SM_RefreshMonster {
    /**
     * 怪物轮数
     */
    private int round;

    public static SM_RefreshMonster valueOf(int round) {
        SM_RefreshMonster sm = new SM_RefreshMonster();
        sm.setRound(round);
        return sm;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }
}
