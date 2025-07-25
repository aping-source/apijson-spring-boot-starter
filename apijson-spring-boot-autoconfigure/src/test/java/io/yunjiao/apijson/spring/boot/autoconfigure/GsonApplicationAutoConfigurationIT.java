package io.yunjiao.apijson.spring.boot.autoconfigure;


import io.yunjiao.apijson.gson.GsonCreator;
import io.yunjiao.apijson.gson.GsonRestController;
import io.yunjiao.apijson.gson.GsonSimpleCallback;
import io.yunjiao.apijson.util.ApijsonConsts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link GsonApplicationAutoConfiguration} 单元测试用户。 测试未通过，框架集成gson还存在问题
 *
 * @author yangyunjiao
 */
public class GsonApplicationAutoConfigurationIT {
    private ApplicationContextRunner applicationContextRunner;

    @BeforeEach
    public void setUp() {
        applicationContextRunner = new ApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(ApijsonAutoConfiguration.class));
    }

    @Test
    public void testNewIdSnowflakeStrategy() {

        applicationContextRunner
                .withPropertyValues(ApijsonConsts.PROPERTY_PREFIX_APIJSON_APPLICATION + "=" + ApijsonProperties.Application.gson,
                        ApijsonConsts.PROPERTY_PREFIX_APIJSON_NEWIDSTRATEGY + "=" + ApijsonProperties.NewIdStrategy.timestamp,
                        ApijsonConsts.PROPERTY_PREFIX_APIJSON + ".enableOnStartup=true",
                        //ApijsonConsts.PROPERTY_PREFIX_APIJSON + ".shutdownWhenServerError=false",
                        ApijsonConsts.PROPERTY_PREFIX_APIJSON_SQL + ".config.defaultDatabase=MYSQL",
                        ApijsonConsts.PROPERTY_PREFIX_APIJSON_SQL + ".config.defaultSchema=apijson_ut",
                        ApijsonConsts.PROPERTY_PREFIX_APIJSON_SQL + ".config.version=8.0.11")
                .withUserConfiguration(DataSourceTestConfiguration.class)
                .run(context -> {
                    assertThat(context).hasSingleBean(GsonCreator.class);
                    assertThat(context).hasSingleBean(GsonSimpleCallback.class);
                    assertThat(context).hasSingleBean(GsonRestController.class);
                });
    }


}
