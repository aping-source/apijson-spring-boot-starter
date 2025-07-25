package io.yunjiao.apijson.example.common.gson;

import apijson.gson.APIJSONFunctionParser;
import io.yunjiao.apijson.gson.GsonCreator;
import io.yunjiao.apijson.spring.boot.autoconfigure.ApijsonSqlProperties;

import javax.sql.DataSource;
import java.io.Serializable;

/**
 * 自定义
 *
 * @author yangyunjiao
 */
public class CustomGsonCreator extends GsonCreator {
    public CustomGsonCreator(DataSource dataSource, ApijsonSqlProperties sqlProperties) {
        super(dataSource, sqlProperties);
    }

    @Override
    public APIJSONFunctionParser<Serializable> createFunctionParser() {
        return new CustomGsonFunctionParser();
    }
}
