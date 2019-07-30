package com.game.world.union.packet;

/**
 * 申请加入行会
 *
 * @Author：xuxin
 * @Date: 2019/7/29 11:09
 */
public class CM_ApplyJoinUnion {
    /**
     * 行会id
     */
    private String unionId;

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }
}
