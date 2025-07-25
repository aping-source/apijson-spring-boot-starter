package io.yunjiao.apijson.spring.boot.autoconfigure;

import io.yunjiao.apijson.util.ApijsonConsts;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * APIJSON 配置属性
 *
 * @author yangyunjiao
 */
@Data
@ConfigurationProperties(prefix = ApijsonConsts.PROPERTY_PREFIX_APIJSON)
public class ApijsonProperties {
    private String restPrefix = "api-json";

    /**
     * 请求需要校验，默认true，开发时应该设置成false
     */
    private boolean needVerifyLogin = true;

    /**
     * 请求需要校验，默认true，开发时应该设置成false
     */
    private boolean needVerifyRole = true;

    /**
     * 请求需要校验，默认true
     */
    private boolean needVerifyContent = true;

    /**
     * 启动时初始化， 默认false
     */
    private boolean enableOnStartup = false;

    /**
     * 服务异常时停止
     */
    private boolean shutdownWhenServerError = true;

    /**
     * apijson debug日志输出，默认false
     */
    private boolean logDebug = false;

    /**
     * 应用程序类型， 默认fastjson2
     */
    private Application application = Application.fastjson2;

    /**
     * 主键生成策略，默认timestamp
     */
    private NewIdStrategy newIdStrategy = NewIdStrategy.timestamp;

    public enum Application {
        fastjson2, gson
    }

    /**
     * 主键生成策略
     */
    public enum NewIdStrategy {
        /**
         * 数据库自增
         */
        database,

        /**
         * uuid组件
         */
        uuid,

        /**
         * 当前时间毫秒数
         */
        timestamp,

        /**
         * 雪花算法
         */
        snowflake,

        /**
         * 用户自定义。需要实现 {@link io.yunjiao.apijson.orm.NewIdStrategy} 接口并配置
         */
        custom
    }
}
