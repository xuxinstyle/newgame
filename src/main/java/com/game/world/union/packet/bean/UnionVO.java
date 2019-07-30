package com.game.world.union.packet.bean;

/**
 * 工会信息
 *
 * @Author：xuxin
 * @Date: 2019/7/29 16:42
 */
public class UnionVO {
    /**
     * 工会id
     */
    private String unionId;
    /**
     * 工会名称
     */
    private String unionName;
    /**
     * 会长id
     */
    private String presidentId;
    /**
     * 工会当前人数
     */
    private int currNum;
    /**
     * 工会最大人数
     */
    private int maxNum;

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getUnionName() {
        return unionName;
    }

    public void setUnionName(String unionName) {
        this.unionName = unionName;
    }

    public String getPresidentId() {
        return presidentId;
    }

    public void setPresidentId(String presidentId) {
        this.presidentId = presidentId;
    }

    public int getCurrNum() {
        return currNum;
    }

    public void setCurrNum(int currNum) {
        this.currNum = currNum;
    }

    public int getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(int maxNum) {
        this.maxNum = maxNum;
    }
}
