package io.yunjiao.apijson.orm;

import apijson.RequestMethod;

import java.io.Serializable;

/**
 * 主键生成策略
 *
 * @author yangyunjiao
 */
public interface NewIdStrategy {

    Serializable newId(RequestMethod method, String database, String schema, String datasource, String table);
}
