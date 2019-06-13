package com.game.scence.packet;

/**
 * @Author：xuxin
 * @Date: 2019/6/5 17:06
 */
public class SM_EnterMap {
    /** 移动状态 1 成功 2 失败*/
    private int status;
    /** mapId*/
    private int mapId;
    /**
     * 地图内容
     */
    private String context;
    /**
     * 账号Id
     */
    private String position;
    private String accountId;

    private int x;
    private int y;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
