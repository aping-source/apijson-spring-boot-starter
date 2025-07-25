package io.yunjiao.apijson.orm;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * {@link LinkedHashMap} 子类
 *
 * @author yangyunjiao
 */
public class GsonMap<K, V> extends LinkedHashMap<K, V> {
    public static <K, V> GsonMap<K, V> of(Map<K, V> map) {
        GsonMap<K, V> gsonMap = new GsonMap<>();
        if (Objects.nonNull(map)) {
            gsonMap.putAll(map);
        }
        return gsonMap;
    }

    public Map<K, V> getJSONObject(K key) {
        return (Map)get(key);

    }

    public String getString(K key) {
        V value = get(key);
        if (Objects.nonNull(value)) {
            return value.toString();
        }

        return null;
    }

    public int getIntValue(K key) {
        V value = get(key);
        if (Objects.nonNull(value)) {
            return Integer.parseInt(value.toString());
        }

        return 0;
    }

    public Boolean getBoolean(K key) {
        V value = get(key);
        if (Objects.nonNull(value)) {
            return Boolean.valueOf(value.toString());
        }

        return null;
    }

    public boolean getBooleanValue(K key) {
        V value = get(key);
        if (Objects.nonNull(value)) {
            return Boolean.parseBoolean(value.toString());
        }

        return false;
    }

    public long getLongValue(K key) {
        V value = get(key);
        if (Objects.nonNull(value)) {
            return Long.parseLong(value.toString());
        }

        return 0L;
    }
}
