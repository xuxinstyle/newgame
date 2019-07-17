package com.game.util;

import com.game.base.attribute.Attribute;
import com.game.scence.visible.model.Position;

/**
 * @Author：xuxin
 * @Date: 2019/7/10 11:19
 */
public class ComputeUtil {
    /**
     * 计算两点之间的距离
     * @param position
     * @param position1
     * @return
     */
    public static double computeDis(Position position, Position position1) {
        return Math.sqrt(Math.pow(position.getX() - position1.getX(),2)+Math.pow(position.getY() - position1.getY(),2));
    }

    /**
     *
     *   减伤百分比＝x/(x＋602) x为防御值
     *
     * 计算真实扣血量伤害
     * @param hurtValue 伤害值
     * @param defAttributeValue 防御属性值
     * @return
     */
    public static double getRealHurt(double hurtValue, long defAttributeValue) {
        double rate = defAttributeValue / (defAttributeValue + 602.0);
        return hurtValue * (1 - rate);
    }

    /**
     * 计算技能的固定伤害加属性伤害百分比伤害的理论值
     * @param attributeValue
     * @param hurt
     * @param prop
     * @return
     */
    public static double getHurtValue(long attributeValue, long hurt, long prop) {
        return attributeValue * prop / 100.0 + hurt;
    }

    /**
     * 根据攻击者 的属性和被攻击者的属性计算最终伤害
     *
     * @param useAttackAttr     使用技能者的攻击属性
     * @param targetDefenseAttr 目标的防御属性
     * @param fixedHurt         技能固定伤害
     * @param hurtProp          技能的属性伤害比例
     * @return
     */
    public static double getRealHurt(Attribute useAttackAttr, Attribute targetDefenseAttr, long fixedHurt, long hurtProp) {
        double hurtValue = getHurtValue(useAttackAttr.getValue(), fixedHurt, hurtProp);
        return getRealHurt(hurtValue, targetDefenseAttr.getValue());
    }

}
