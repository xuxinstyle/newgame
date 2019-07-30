package com.game.world.union.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 工会职务 权限
 *
 * @Author：xuxin
 * @Date: 2019/7/29 9:01
 */
public enum UnionJob {
    /**
     * 成员
     */
    MEMBER(1),
    /**
     * 精英
     */
    ELITE(2),
    /**
     * 长老
     */
    ELDER(3),
    /**
     * 副会长 副会长以上成员有同意加入工会权限
     */
    VICE_PRESIDENT(4),
    /**
     * 会长
     */
    PRESIDENT(5),;
    private static Map<Integer, UnionJob> unionJobMap = new HashMap<>();

    static {
        for (UnionJob job : UnionJob.values()) {
            unionJobMap.put(job.getPermission(), job);
        }
    }

    private int permission;

    UnionJob(int permission) {
        this.permission = permission;
    }

    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }

    public static UnionJob getUnionJob(int permission) {
        return unionJobMap.get(permission);
    }
}
