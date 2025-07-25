package io.yunjiao.apijson.spring.boot.autoconfigure;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import java.util.Objects;

/**
 * Snowflake雪花算法 自动配置类
 *
 * @author yangyunjiao
 * @see Snowflake
 */
@Slf4j
@RequiredArgsConstructor
@AutoConfiguration
public class SnowflakeAutoConfiguration {
    public static final   String SNOWFLAKE_WORKER_ID = "SNOWFLAKE_WORKER_ID";

    public static final  String SNOWFLAKE_DATACENTER_ID = "SNOWFLAKE_DATACENTER_ID";

    private final Environment env;

    @PostConstruct
    public void postConstruct() {
        log.info("Snowflake Auto Configuration");
    }

    /**
     * 获取系统变量 {@value SNOWFLAKE_WORKER_ID} 和 {@value SNOWFLAKE_DATACENTER_ID} 值创建 {@link Snowflake} 实例
     *
     * @return {@link Snowflake} 实例
     */
    @Bean
    @ConditionalOnMissingBean
    public Snowflake snowflake() {
        long workerId = 1L;
        try {
            String workIdEnv = env.getProperty(SNOWFLAKE_WORKER_ID);
            if (Objects.nonNull(workIdEnv)) {
                workerId = Long.parseLong(workIdEnv);
            }
        } catch (Exception ignored) {

        }

        long datacenterId = 1L;
        try {
            String datacenterIdEnv = env.getProperty(SNOWFLAKE_DATACENTER_ID);
            if (Objects.nonNull(datacenterIdEnv)) {
                datacenterId = Long.parseLong(datacenterIdEnv);
            }
        } catch (Exception ignored) {

        }

        Snowflake snowflake = IdUtil.getSnowflake(workerId, datacenterId);
        if (log.isDebugEnabled()) {
            log.debug("Configure Bean [Snowflake], workerId={}, datacenterId={}", workerId, datacenterId);
        }
        return snowflake;
    }

}
