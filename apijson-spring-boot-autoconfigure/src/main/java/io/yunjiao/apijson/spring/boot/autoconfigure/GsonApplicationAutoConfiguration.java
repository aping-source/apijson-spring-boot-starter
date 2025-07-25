package io.yunjiao.apijson.spring.boot.autoconfigure;

import apijson.gson.*;
import io.yunjiao.apijson.gson.*;
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
 * Gson 应用自动配置
 *
 * @author yangyunjiao
 */
@Slf4j
@RequiredArgsConstructor
@AutoConfiguration(after = {ApijsonInitAutoConfiguration.class})
@Conditional(ApllicationCondition.OnGson.class)
public class GsonApplicationAutoConfiguration {
    private final IdKeyStrategy idKeyStrategy;

    private final NewIdStrategy newIdStrategy;

    private final DataSource dataSource;

    private final ApijsonSqlProperties sqlProperties;

    private final ApijsonProperties properties;


    @PostConstruct
    public void postConstruct() {
        log.info("Gson Application Auto Configuration");
        forceInitClass();
    }

    @Bean
    @ConditionalOnMissingBean
    GsonCreator gsonCreator() {
        GsonCreator bean = new GsonCreator(dataSource, sqlProperties);
        if (log.isDebugEnabled()) {
            log.debug("Configure Bean [Gson Creator]");
        }
        return bean;
    }

    @Bean
    @ConditionalOnMissingBean
    GsonSimpleCallback gsonSimpleCallback() {
        GsonSimpleCallback bean = new GsonSimpleCallback(idKeyStrategy, newIdStrategy);
        if (log.isDebugEnabled()) {
            log.debug("Configure Bean [Gson Simple Callback]");
        }
        return bean;
    }

    @Bean
    GsonRestController gsonRestController() {
        GsonRestController bean = new GsonRestController(properties);
        if (log.isDebugEnabled()) {
            log.debug("Configure Bean [Gson Rest Controller]");
        }
        return bean;
    }

    @Bean
    GsonEXtRestController gsonEXtRestController() {
        GsonEXtRestController bean = new GsonEXtRestController();
        if (log.isDebugEnabled()) {
            log.debug("Configure Bean [Gson Ext Rest Controller]");
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
    class GsonApplicationInitConfiguration {
        private final GsonSimpleCallback gsonSimpleCallback;

        private final GsonCreator gsonCreator;

        private final ApijsonProperties properties;

        @PostConstruct
        public void postConstruct() throws Exception {
            log.info("Gson Application Init");

            GsonSqlConfig.SIMPLE_CALLBACK = gsonSimpleCallback;
            APIJSONApplication.init(properties.isShutdownWhenServerError(), gsonCreator);
            if (properties.isEnableOnStartup()) {
                GsonVerifier.init(false, gsonCreator);
            }
        }
    }
}
