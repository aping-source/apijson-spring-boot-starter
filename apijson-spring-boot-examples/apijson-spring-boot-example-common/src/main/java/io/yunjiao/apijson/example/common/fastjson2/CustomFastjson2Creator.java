package io.yunjiao.apijson.example.common.fastjson2;

import apijson.fastjson2.APIJSONFunctionParser;
import io.yunjiao.apijson.fastjson2.Fastjson2Creator;
import io.yunjiao.apijson.spring.boot.autoconfigure.ApijsonSqlProperties;

import javax.sql.DataSource;
import java.io.Serializable;

/**
 * 自定义
 *
 * @author yangyunjiao
 */
public class CustomFastjson2Creator extends Fastjson2Creator {
    public CustomFastjson2Creator(DataSource dataSource, ApijsonSqlProperties sqlProperties) {
        super(dataSource, sqlProperties);
    }

    @Override
    public APIJSONFunctionParser<Serializable> createFunctionParser() {
        return new CustomFastjson2FunctionParser();
    }
}
