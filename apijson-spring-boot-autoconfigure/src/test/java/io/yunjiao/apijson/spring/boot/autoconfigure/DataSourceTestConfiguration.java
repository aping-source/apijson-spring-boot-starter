package io.yunjiao.apijson.spring.boot.autoconfigure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

/**
 * 单元测试使用的数据源
 *
 * @author yangyunjiao
 */
@Configuration
public class DataSourceTestConfiguration {
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/apijson_ut?userSSL=false&serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=UTF-8"); // 内存模式
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        return dataSource;
    }


    @Bean
    public DataSourceInitializer dataSourceInitializer(DataSource dataSource) {
        DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);

        // 创建脚本执行器
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("db/access.sql"));
        populator.addScript(new ClassPathResource("db/request.sql"));
        populator.addScript(new ClassPathResource("db/function.sql"));
        populator.addScript(new ClassPathResource("db/script.sql"));
        populator.setSeparator(";"); // 语句分隔符（可选）

        initializer.setDatabasePopulator(populator);
        initializer.setEnabled(true); // 启用初始化

        return initializer;
    }
}
