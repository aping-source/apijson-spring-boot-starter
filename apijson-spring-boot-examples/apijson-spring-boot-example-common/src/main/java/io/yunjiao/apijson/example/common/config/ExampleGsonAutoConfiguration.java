package io.yunjiao.apijson.example.common.config;

import io.yunjiao.apijson.example.common.gson.CustomGsonCreator;
import io.yunjiao.apijson.spring.boot.autoconfigure.ApijsonSqlProperties;
import io.yunjiao.apijson.spring.boot.autoconfigure.condition.ApllicationCondition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * 通用配置
 *
 * @author yangyunjiao
 */
@Slf4j
@Conditional(ApllicationCondition.OnGson.class)
@ConditionalOnBean(DataSource.class)
@Configuration
public class ExampleGsonAutoConfiguration {
    @Bean
    public CustomGsonCreator customGsonCreator(DataSource dataSource,
                                               ApijsonSqlProperties sqlProperties) {
        log.debug("Configure Bean [Example Gson Auto Configuration]");
        return new CustomGsonCreator(dataSource, sqlProperties);
    }
}
