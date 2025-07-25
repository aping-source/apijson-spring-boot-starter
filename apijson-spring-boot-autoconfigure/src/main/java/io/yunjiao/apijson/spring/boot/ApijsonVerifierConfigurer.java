package io.yunjiao.apijson.spring.boot;

import apijson.RequestMethod;
import apijson.orm.Entry;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.regex.Pattern;

/**
 * AbstractVerifier 用户自定义配置
 *
 * @author yangyunjiao
 */
@FunctionalInterface
public interface ApijsonVerifierConfigurer {
    /**
     * 配置
     *
     */
    void configure(Map<String, Entry<String, Object>> roleMap,
                   List<String> operationKeyList,
                   Map<String, Map<RequestMethod, String[]>> systemAccessMap,
                   Map<String, Map<RequestMethod, String[]>> accessNap,
                   Map<String, Pattern> compileMap,
                   Map<String, SortedMap<Integer, Map<String, Object>>> requestMap);
}
