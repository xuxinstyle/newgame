package com.game.base.account.model;

import com.game.scence.constant.SceneType;

/**
 * 放账号的信息，如属性等
 * @Author：xuxin
 * @Date: 2019/5/18 16:26
 */

public class AccountInfo {

    /**
     * 账号昵称
     */
    private String nickName;

    /**
     *  玩家职业
     */
    private String career;

    /**
     *  玩家当前所在的场景类型
     */
    private SceneType currentMapType;
    /**
     *  玩家的生存状态 生存，死亡 1 生存，2 死亡
     */
    private int surviveStatus;

    public static AccountInfo valueOf(){
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setCurrentMapType(SceneType.NoviceVillage);
        accountInfo.setSurviveStatus(1);
        accountInfo.setCareer(null);
        accountInfo.setNickName(null);
        return accountInfo;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public SceneType getCurrentMapType() {
        return currentMapType;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public void setCurrentMapType(SceneType currentMapType) {
        this.currentMapType = currentMapType;
    }

    public int getSurviveStatus() {
        return surviveStatus;
    }

    public void setSurviveStatus(int surviveStatus) {
        this.surviveStatus = surviveStatus;
    }

    @Override
    public String toString() {
        return "AccountInfo{" +
                "nickName='" + nickName + '\'' +
                ", career='" + career + '\'' +
                ", currentMapType=" + currentMapType +
                ", surviveStatus=" + surviveStatus +
                '}';
    }
}
