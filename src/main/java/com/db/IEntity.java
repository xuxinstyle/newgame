package com.db;

import java.io.Serializable;

/**
 * @Author：xuxin
 * @Date: 2019/6/3 15:43
 */
public interface IEntity<PK extends Serializable & Comparable<PK>> {
    PK getId();

    void serialize();

    void unserialize();
}
