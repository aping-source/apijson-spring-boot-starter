package io.yunjiao.apijson.exceptions;

/**
 * 未正确配置: {@value io.yunjiao.apijson.util.ApijsonConsts#PROPERTY_PREFIX_APIJSON_NEWIDSTRATEGY}
 *
 * @author yangyunjiao
 */
public class NewIdCustomStrategyException extends RuntimeException{
    public NewIdCustomStrategyException() {
        super("自定义'NewIdCustomStrategy'子类未正确配置");
    }
}
