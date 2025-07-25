package io.yunjiao.apijson.orm;

import apijson.RequestMethod;
import io.yunjiao.apijson.exceptions.NewIdCustomStrategyException;

import java.io.Serializable;

/**
 * 主键自动生成，用户自定义。
 *
 * @author yangyunjiao
 * @see NewIdStrategy
 */
public class NewIdCustomStrategy implements NewIdStrategy {
    @Override
    public Serializable newId(RequestMethod method, String database, String schema, String datasource, String table) {
        throw new NewIdCustomStrategyException();
    }
}
