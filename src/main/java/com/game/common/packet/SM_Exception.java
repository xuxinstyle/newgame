package com.game.common.packet;

/**
 * @Author：xuxin
 * @Date: 2019/7/17 18:01
 */
public class SM_Exception {
    /**
     * 错误码
     */
    private int i18nId;

    public static SM_Exception valueOf(int i18nId) {
        SM_Exception sm = new SM_Exception();
        sm.setI18nId(i18nId);
        return sm;
    }

    public int getI18nId() {
        return i18nId;
    }

    public void setI18nId(int i18nId) {
        this.i18nId = i18nId;
    }
}
