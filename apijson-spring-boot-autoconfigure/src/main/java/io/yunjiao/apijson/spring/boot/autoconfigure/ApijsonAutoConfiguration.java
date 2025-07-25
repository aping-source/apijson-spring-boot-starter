package io.yunjiao.apijson.spring.boot.autoconfigure;

import io.yunjiao.apijson.annotation.ApijsonRest;
import io.yunjiao.apijson.orm.IdKeyApijsonStrategy;
import io.yunjiao.apijson.orm.IdKeyStrategy;
import jakarta.annotation.Nonnull;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;

/**
 * APIJSON 自动配置
 *
 * @author yangyunjiao
 */
@Slf4j
@AutoConfiguration
@RequiredArgsConstructor
@ConditionalOnBean(DataSource.class)
@EnableConfigurationProperties({ApijsonProperties.class, ApijsonSqlProperties.class,
        ApijsonParserProperties.class, ApijsonVerifierProperties.class})
@Import({
        SnowflakeAutoConfiguration.class,
        NewIdStrategyAutoConfiguration.class,
        ApijsonInitAutoConfiguration.class,
        Fastjson2ApplicationAutoConfiguration.class,
        GsonApplicationAutoConfiguration.class
})
public class ApijsonAutoConfiguration {
    private final ApijsonProperties apijsonProperties;

    @PostConstruct
    public void postConstruct() {
        log.info("APIJSON Auto Configuration");
    }

    @Bean
    @ConditionalOnMissingBean
    IdKeyStrategy idKeyApijsonStrategy() {
        IdKeyStrategy bean = new IdKeyApijsonStrategy();
        if (log.isDebugEnabled()) {
            log.debug("Configure Bean [Id Key APIJSON Strategy]");
        }
        return bean;
    }


    @Bean
    public WebMvcConfigurer configurePathMatch() {
        // rest接口统一前缀
        return new WebMvcConfigurer() {
            @Override
            public void configurePathMatch(@Nonnull PathMatchConfigurer configurer) {
                configurer.addPathPrefix(apijsonProperties.getRestPrefix(),
                        c -> c.isAnnotationPresent(ApijsonRest.class));
            }
        };
    }
}
