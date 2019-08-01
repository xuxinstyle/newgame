package com.game.role.battlescore.util;

import com.game.base.attribute.Attribute;
import com.game.base.attribute.constant.AttributeType;
import com.game.base.attribute.container.CreatureAttributeContainer;
import com.game.role.player.model.Player;

import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/7/31 14:26
 */
public class BattleScoreCompute {
    /**
     * 物理攻击
     */
    private final static int PHYSICAL_ATTACK = 100;
    /**
     * 法术攻击
     */
    private final static int MAGIC_ATTACK = 100;
    /**
     * 物理防御
     */
    private final static int PHYSICAL_DEFENSE = 100;
    /**
     * 法术防御
     */
    private final static int MAGIC_DEFENSE = 100;
    /**
     * 最大血量
     */
    private final static int MAX_HP = 100;
    /**
     * 最大蓝量
     */
    private final static int MAX_MP = 100;
    /**
     * 物理穿透
     */
    private final static int PHYSICAL_PENETRATION = 100;
    /**
     * 法术穿透
     */
    private final static int SPEEL_PENETRATION = 100;

    public static long computeBattleScore(Map<AttributeType, Attribute> attrs) {
        double battleScore = 0.0;
        for (Attribute attribute : attrs.values()) {
            battleScore += attribute.getType().getScore() * attribute.getValue();
        }
        return (long) battleScore;
    }

    public static long computePlayerBattleScore(Player player) {
        CreatureAttributeContainer attributeContainer = player.getAttributeContainer();
        Map<AttributeType, Attribute> finalAttributes = attributeContainer.getFinalAttributes();

        return computeBattleScore(finalAttributes);
    }


}
