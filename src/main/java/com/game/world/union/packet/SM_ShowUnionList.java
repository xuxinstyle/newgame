package com.game.world.union.packet;

import com.game.world.union.packet.bean.UnionVO;

import java.util.List;

/**
 * @Author：xuxin
 * @Date: 2019/7/29 17:03
 */
public class SM_ShowUnionList {
    /**
     * 工会列表
     */
    List<UnionVO> unionVOList;

    public List<UnionVO> getUnionVOList() {
        return unionVOList;
    }

    public void setUnionVOList(List<UnionVO> unionVOList) {
        this.unionVOList = unionVOList;
    }
}
