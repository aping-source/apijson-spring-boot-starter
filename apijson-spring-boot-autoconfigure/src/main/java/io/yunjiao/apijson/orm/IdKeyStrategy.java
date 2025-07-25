package io.yunjiao.apijson.orm;

/**
 * 主键名称策略
 *
 * @author yangyunjiao
 */
public interface IdKeyStrategy {
    String getIdKey(String database, String schema, String datasource, String table);
}
