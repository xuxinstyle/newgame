package com.game.gm.model;

import com.game.SpringContext;
import com.socket.core.session.TSession;

/**
 * @Author：xuxin
 * @Date: 2019/6/28 20:02
 */
public class GMCommand {
    /**
     * 发道具给玩家
     * @param accountId
     * @param itemModelId
     * @param num
     */
    public void addItem(String accountId,int itemModelId, int num){

        SpringContext.getItemService().gmAwardToPack(accountId, itemModelId, num);
    }

}
