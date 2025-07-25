package io.yunjiao.apijson.spring.boot.autoconfigure;

import io.yunjiao.apijson.fastjson2.Fastjson2Creator;
import io.yunjiao.apijson.fastjson2.Fastjson2RestController;
import io.yunjiao.apijson.fastjson2.Fastjson2SimpleCallback;
import io.yunjiao.apijson.util.ApijsonConsts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link Fastjson2ApplicationAutoConfiguration} 单元测试用户
 *
 * @author yangyunjiao
 */
public class Fastjson2ApplicationAutoConfigurationIT {
    private ApplicationContextRunner applicationContextRunner;

    @BeforeEach
    public void setUp() {
        applicationContextRunner = new ApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(ApijsonAutoConfiguration.class));
    }

    @Test
    public void testNewIdSnowflakeStrategy() {

        applicationContextRunner
                .withPropertyValues(ApijsonConsts.PROPERTY_PREFIX_APIJSON_APPLICATION + "=" + ApijsonProperties.Application.fastjson2,
                        ApijsonConsts.PROPERTY_PREFIX_APIJSON_NEWIDSTRATEGY + "=" + ApijsonProperties.NewIdStrategy.timestamp,
                        ApijsonConsts.PROPERTY_PREFIX_APIJSON + ".enableOnStartup=true",
                        ApijsonConsts.PROPERTY_PREFIX_APIJSON_SQL + ".config.defaultDatabase=MYSQL",
                        ApijsonConsts.PROPERTY_PREFIX_APIJSON_SQL + ".config.defaultSchema=apijson_ut",
                        ApijsonConsts.PROPERTY_PREFIX_APIJSON_SQL + ".config.version=8.0.11")
                .withUserConfiguration(DataSourceTestConfiguration.class)
                .run(context -> {
                    assertThat(context).hasSingleBean(Fastjson2Creator.class);
                    assertThat(context).hasSingleBean(Fastjson2SimpleCallback.class);
                    assertThat(context).hasSingleBean(Fastjson2RestController.class);
                });
    }


}
