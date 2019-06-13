package com.game.base.executor.common.Impl;

import com.game.base.executor.AbstractCommand;

/**
 * @Author：xuxin
 * @Date: 2019/6/12 12:06
 */
public abstract class AbstractCommonCommand extends AbstractCommand {

    @Override
    public Object getKey() {
        return getName();
    }

    @Override
    public int modIndex(int poolsize) {
        return Math.abs(getName().hashCode() % poolsize);
    }
}
