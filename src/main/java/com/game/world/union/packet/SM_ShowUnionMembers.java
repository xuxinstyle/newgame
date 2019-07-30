package com.game.world.union.packet;

import com.game.world.union.packet.bean.UnionMemberVO;
import com.game.world.union.packet.bean.UnionVO;

import java.util.List;

/**
 * @Author：xuxin
 * @Date: 2019/7/29 16:51
 */
public class SM_ShowUnionMembers {
    /**
     * 工会信息
     */
    private UnionVO unionVO;
    /**
     * 工会成员信息
     */
    List<UnionMemberVO> unionMemberVOList;

    public List<UnionMemberVO> getUnionMemberVOList() {
        return unionMemberVOList;
    }

    public void setUnionMemberVOList(List<UnionMemberVO> unionMemberVOList) {
        this.unionMemberVOList = unionMemberVOList;
    }

    public UnionVO getUnionVO() {
        return unionVO;
    }

    public void setUnionVO(UnionVO unionVO) {
        this.unionVO = unionVO;
    }
}
