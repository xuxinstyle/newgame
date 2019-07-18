package com.game.role.skilleffect.packet;

/**
 * @Author：xuxin
 * @Date: 2019/7/18 16:36
 */
public class SM_ShowUnitEffect {
    /**
     * buff类型
     */
    private String buffName;

    public static SM_ShowUnitEffect valueOf(String effectName) {
        SM_ShowUnitEffect sm = new SM_ShowUnitEffect();
        sm.setBuffName(effectName);
        return sm;
    }

    public String getBuffName() {
        return buffName;
    }

    public void setBuffName(String buffName) {
        this.buffName = buffName;
    }
}
