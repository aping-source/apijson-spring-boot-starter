package io.yunjiao.apijson.fastjson2;

import apijson.fastjson2.*;
import io.yunjiao.apijson.spring.boot.autoconfigure.ApijsonSqlProperties;
import lombok.RequiredArgsConstructor;

import javax.sql.DataSource;
import java.io.Serializable;

/**
 * framework创建器
 *
 * @author yangyunjiao
 */

@RequiredArgsConstructor
public class Fastjson2Creator extends APIJSONCreator<Serializable> {
    private final DataSource dataSource;

    private final ApijsonSqlProperties sqlProperties;

    @Override
    public APIJSONParser<Serializable> createParser() {
        return new Fastjson2Parser();
    }

    @Override
    public APIJSONFunctionParser<Serializable> createFunctionParser() {
        return new Fastjson2FunctionParser();
    }

    @Override
    public APIJSONVerifier<Serializable> createVerifier() {
        return new Fastjson2Verifier();
    }

    @Override
    public APIJSONSQLConfig<Serializable> createSQLConfig() {
        return new Fastjson2SqlConfig(sqlProperties);
    }

    @Override
    public APIJSONSQLExecutor<Serializable> createSQLExecutor() {
        return new Fastjson2SqlExecutor(dataSource);
    }
}
