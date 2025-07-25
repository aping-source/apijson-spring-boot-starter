package io.yunjiao.apijson.example.common.config;

import apijson.orm.script.JavaScriptExecutor;
import apijson.orm.script.ScriptExecutor;
import io.yunjiao.apijson.spring.boot.ApijsonFunctionParserConfigurer;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

/**
 * 远程函数配置
 *
 * @author yangyunjiao
 */
@Configuration
public class ExampleFunctionParserConfigurer implements ApijsonFunctionParserConfigurer {
    @Override
    public void configure(Map<String, ScriptExecutor<?, ? extends Map<String, Object>, ? extends List<Object>>> scriptExecutorMap, Map<String, Map<String, Object>> functionMap) {
        scriptExecutorMap.put("js", new JavaScriptExecutor<>());
    }
}
