package io.yunjiao.apijson.spring.boot.autoconfigure;

import apijson.Log;
import apijson.fastjson2.*;
import io.yunjiao.apijson.fastjson2.*;
import io.yunjiao.apijson.orm.IdKeyStrategy;
import io.yunjiao.apijson.orm.NewIdStrategy;
import io.yunjiao.apijson.spring.boot.autoconfigure.condition.ApllicationCondition;
import io.yunjiao.apijson.util.ApijsonUtils;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;

import javax.sql.DataSource;

/**
 * Fastjson2 应用自动配置
 *
 * @author yangyunjiao
 */
@Slf4j
@RequiredArgsConstructor
@AutoConfiguration(after = {ApijsonInitAutoConfiguration.class})
@Conditional(ApllicationCondition.OnFastjson2.class)
public class Fastjson2ApplicationAutoConfiguration {
    private final IdKeyStrategy idKeyStrategy;

    private final NewIdStrategy newIdStrategy;

    private final DataSource dataSource;

    private final ApijsonSqlProperties sqlProperties;

    private final ApijsonProperties properties;


    @PostConstruct
    public void postConstruct() {
        log.info("Fastjson2 Application Auto Configuration");
        forceInitClass();
    }

    @Bean
    @ConditionalOnMissingBean
    Fastjson2Creator fastjson2Creator() {
        Fastjson2Creator bean = new Fastjson2Creator(dataSource, sqlProperties);
        if (log.isDebugEnabled()) {
            log.debug("Configure Bean [Fastjson2 Creator]");
        }
        return bean;
    }

    @Bean
    @ConditionalOnMissingBean
    Fastjson2SimpleCallback fastjson2SimpleCallback() {
        Fastjson2SimpleCallback bean = new Fastjson2SimpleCallback(idKeyStrategy, newIdStrategy);
        if (log.isDebugEnabled()) {
            log.debug("Configure Bean [Fastjson2 Simple Callback]");
        }
        return bean;
    }

    @Bean
    Fastjson2RestController fastjson2RestController() {
        Fastjson2RestController bean = new Fastjson2RestController(properties);
        if (log.isDebugEnabled()) {
            log.debug("Configure Bean [Fastjson2 Rest Controller]");
        }
        return bean;
    }

    @Bean
    Fastjson2EXtRestController fastjson2ExtRestController() {
        Fastjson2EXtRestController bean = new Fastjson2EXtRestController();
        if (log.isDebugEnabled()) {
            log.debug("Configure Bean [Fastjson2 Rest Ext Controller]");
        }
        return bean;
    }


    void forceInitClass() {
        ApijsonUtils.forceInit(APIJSONApplication.class);
        ApijsonUtils.forceInit(APIJSONController.class);
        ApijsonUtils.forceInit(APIJSONFunctionParser.class);
        ApijsonUtils.forceInit(APIJSONObjectParser.class);
        ApijsonUtils.forceInit(APIJSONParser.class);
        ApijsonUtils.forceInit(APIJSONSQLConfig.class);
        ApijsonUtils.forceInit(APIJSONSQLExecutor.class);
        ApijsonUtils.forceInit(APIJSONVerifier.class);
    }

    /**
     * 初始化
     */
    @RequiredArgsConstructor
    @AutoConfiguration
    static
    class Fastjson2ApplicationInitConfiguration {
        private final Fastjson2SimpleCallback fastjson2SimpleCallback;

        private final Fastjson2Creator fastjson2Creator;

        private final ApijsonProperties properties;

        @PostConstruct
        public void postConstruct() throws Exception {
            log.info("Fastjson2 Application Init");

            Fastjson2SqlConfig.SIMPLE_CALLBACK = fastjson2SimpleCallback;
            Log.DEBUG = properties.isLogDebug();
            APIJSONApplication.init(properties.isShutdownWhenServerError(), fastjson2Creator);
            if (properties.isEnableOnStartup()) {
                Fastjson2Verifier.init(false, fastjson2Creator);
            }
        }
    }
}
