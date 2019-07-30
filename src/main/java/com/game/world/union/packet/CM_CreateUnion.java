package com.game.world.union.packet;

/**
 * 请求创建工会
 *
 * @Author：xuxin
 * @Date: 2019/7/29 9:29
 */
public class CM_CreateUnion {
    /**
     * 工会名
     */
    private String unionName;

    public String getUnionName() {
        return unionName;
    }

    public void setUnionName(String unionName) {
        this.unionName = unionName;
    }
}
