package com.db;

import java.io.Serializable;

/**
 * @Author：xuxin
 * @Date: 2019/7/2 15:09
 */
public interface EntityBuilder<PK extends Comparable<PK> &  Serializable, T extends IEntity<PK>> {
    /**
     * create实例
     * @param id
     * @return
     */
    T newInstance(PK id);
}
