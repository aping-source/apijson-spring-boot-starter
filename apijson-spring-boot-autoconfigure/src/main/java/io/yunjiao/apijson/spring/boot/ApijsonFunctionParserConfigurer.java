package io.yunjiao.apijson.spring.boot;

import apijson.orm.script.ScriptExecutor;

import java.util.List;
import java.util.Map;

/**
 * AbstractVerifier 用户自定义配置
 *
 * @author yangyunjiao
 */
@FunctionalInterface
public interface ApijsonFunctionParserConfigurer {
    /**
     * 配置
     *
     */
    void configure(Map<String, ScriptExecutor<?, ? extends Map<String, Object>, ? extends List<Object>>> scriptExecutorMap,
                   Map<String, Map<String, Object>> functionMap);
}
