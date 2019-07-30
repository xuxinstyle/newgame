package com.game.world.union.packet;

import java.util.Set;

/**
 * 申请列表
 *
 * @Author：xuxin
 * @Date: 2019/7/29 12:14
 */
public class SM_ShowApplyList {
    /**
     * 申请列表
     */
    private Set<String> applyList;

    public Set<String> getApplyList() {
        return applyList;
    }

    public void setApplyList(Set<String> applyList) {
        this.applyList = applyList;
    }
}
