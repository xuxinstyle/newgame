package com.game.user.item.packet;

/**
 * @Author：xuxin
 * @Date: 2019/6/14 11:52
 */
public class SM_AwardToPack {
    /**
     * 1 添加成功  2 添加失败 3 背包已满
     * 可以加一个I18错误码，让客户端读取到对应的错误码
     */
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
