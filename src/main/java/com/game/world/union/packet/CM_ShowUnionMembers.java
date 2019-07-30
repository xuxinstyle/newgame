package com.game.world.union.packet;

/**
 * 查看行会的成员信息
 *
 * @Author：xuxin
 * @Date: 2019/7/29 16:38
 */
public class CM_ShowUnionMembers {
    /**
     * 工会id
     */
    private String unionId;

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }
}
