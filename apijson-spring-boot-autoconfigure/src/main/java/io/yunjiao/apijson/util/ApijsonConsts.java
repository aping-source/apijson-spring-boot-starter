package io.yunjiao.apijson.util;

import apijson.orm.SQLConfig;

/**
 * 常量定义
 *
 * @author yangyunjiao
 */
public final class  ApijsonConsts {
    public static final String APIJSON_DELEGATE_ID = "Apijson-Delegate-Id";

    // 配置文件属性定义
    public static final String PROPERTY_ENABLED = ".enabled";

    public static final String PROPERTY_PREFIX_SPRING = "spring";

    public static final String PROPERTY_PREFIX_APIJSON = PROPERTY_PREFIX_SPRING + ".apijson";

    public static final String PROPERTY_PREFIX_APIJSON_SQL = PROPERTY_PREFIX_APIJSON + ".sql";

    public static final String PROPERTY_PREFIX_APIJSON_PARSER = PROPERTY_PREFIX_APIJSON + ".parser";

    public static final String PROPERTY_PREFIX_APIJSON_VERIFIER = PROPERTY_PREFIX_APIJSON + ".verifier";

    public static final String PROPERTY_PREFIX_APIJSON_APPLICATION = PROPERTY_PREFIX_APIJSON + ".application";

    public static final String PROPERTY_PREFIX_APIJSON_NEWIDSTRATEGY = PROPERTY_PREFIX_APIJSON + ".new-id-strategy";

    // 通用常量定义
    public static final String SQL_CONFIG_DEFAULT_DATABASE = SQLConfig.DATABASE_MYSQL;

    public static final String SQL_CONFIG_DEFAULT_SCHEMA = "sys";


    public static final String SQL_EXECUTOR_KEY_RAW_LIST = "@RAW@LIST";

    public static final String SQL_EXECUTOR_KEY_VICE_ITEM = "@VICE@ITEM";
}
