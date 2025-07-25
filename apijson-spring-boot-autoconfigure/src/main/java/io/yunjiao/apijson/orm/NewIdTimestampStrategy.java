package io.yunjiao.apijson.orm;

import apijson.RequestMethod;
import apijson.orm.AbstractSQLConfig;

import java.io.Serializable;

/**
 * 以当前时间毫秒为基数，每次调用都在基数加1，并做为新的基数
 *
 * 此实现未考虑多线程，在多线程下会出现重复值，请谨慎使用！！！
 *
 * @author yangyunjiao
 * @see NewIdStrategy
 */
public class NewIdTimestampStrategy implements NewIdStrategy {
    @Override
    public Serializable newId(RequestMethod method, String database, String schema, String datasource, String table) {
        long id = System.currentTimeMillis();
        if (id <= AbstractSQLConfig.LAST_ID) {
            id = AbstractSQLConfig.LAST_ID + 1L;
        }

        AbstractSQLConfig.LAST_ID = id;
        return id;
    }
}
