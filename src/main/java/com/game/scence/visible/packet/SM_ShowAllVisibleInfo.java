package com.game.scence.visible.packet;

import com.game.scence.visible.packet.bean.VisibleVO;

import java.util.List;

/**
 * @Author：xuxin
 * @Date: 2019/6/6 10:02
 */
public class SM_ShowAllVisibleInfo {

    private List<VisibleVO> visibleVOList;

    public List<VisibleVO> getVisibleVOList() {
        return visibleVOList;
    }

    public void setVisibleVOList(List<VisibleVO> visibleVOList) {
        this.visibleVOList = visibleVOList;
    }
}
