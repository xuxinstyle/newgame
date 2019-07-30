package com.game.world.union.packet;

/**
 * @Author：xuxin
 * @Date: 2019/7/30 10:35
 */
public class CM_UpdatePermission {
    /**
     * 目标账号
     */
    private String targetAccountId;
    /**
     * 目标权限
     */
    private int permission;


    public String getTargetAccountId() {
        return targetAccountId;
    }

    public void setTargetAccountId(String targetAccountId) {
        this.targetAccountId = targetAccountId;
    }

    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }
}
