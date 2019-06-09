package com.resource;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/6/4 10:22
 */
public class StorageData<K, V> {
    private Map<K, V> values = new HashMap<>();

    public Map<K, V> getValues() {
        return values;
    }

    public void setValues(Map<K, V> values) {
        this.values.putAll(values);
    }
}
