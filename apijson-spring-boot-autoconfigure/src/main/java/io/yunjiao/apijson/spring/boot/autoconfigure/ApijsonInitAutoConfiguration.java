package io.yunjiao.apijson.spring.boot.autoconfigure;

import apijson.framework.*;
import io.yunjiao.apijson.spring.boot.ApijsonFunctionParserConfigurer;
import io.yunjiao.apijson.spring.boot.ApijsonSqlConfigConfigurer;
import io.yunjiao.apijson.spring.boot.ApijsonVerifierConfigurer;
import io.yunjiao.apijson.util.ApijsonUtils;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;

import java.rmi.ServerException;
import java.util.stream.Collectors;

/**
 * APIJSON初始化自动配置
 *
 * @author yangyunjiao
 */
@Slf4j
@RequiredArgsConstructor
@AutoConfiguration
public class ApijsonInitAutoConfiguration {
    private final ApijsonParserProperties parserProperties;

    private final ApijsonVerifierProperties verifierProperties;

    private final ApijsonSqlProperties sqlProperties;

    private final ObjectProvider<ApijsonSqlConfigConfigurer> sqlConfigConfigurers;

    private final ObjectProvider<ApijsonVerifierConfigurer> apijsonVerifierConfigurers;

    private final ObjectProvider<ApijsonFunctionParserConfigurer> apijsonFunctionParserConfigurers;

    @PostConstruct
    public void postConstruct() throws ServerException {
        forceInitClass();
        initSqlConfig();
        initSqlExecutor();
        initVerifier();
        initRequestParaser();
        initObjectParser();
        initFunctionParser();
    }

    /**
     * 强迫加载类，执行静态(static {})代码块逻辑
     */
    void forceInitClass() {
        ApijsonUtils.forceInit(APIJSONApplication.class);
        ApijsonUtils.forceInit(APIJSONConstant.class);
        ApijsonUtils.forceInit(APIJSONController.class);
        ApijsonUtils.forceInit(APIJSONFunctionParser.class);
        ApijsonUtils.forceInit(APIJSONObjectParser.class);
        ApijsonUtils.forceInit(APIJSONParser.class);
        ApijsonUtils.forceInit(APIJSONSQLConfig.class);
        ApijsonUtils.forceInit(APIJSONSQLExecutor.class);
        ApijsonUtils.forceInit(APIJSONVerifier.class);
        ApijsonUtils.forceInit(ColumnUtil.class);
    }

    void initSqlConfig() {
        log.info( "APIJSON Sql Config Init");

        ApijsonSqlProperties.Config config = sqlProperties.getConfig();
        ApijsonUtils.buildAPIJSONSQLConfigStatic(config, sqlConfigConfigurers.orderedStream().collect(Collectors.toList()));
        ColumnUtil.init();
    }

    void initSqlExecutor() {
        log.info("APIJSON Sql Executor Init");

        ApijsonSqlProperties.Executor executor = sqlProperties.getExecutor();
        ApijsonUtils.buildAPIJSONSQLExecutorStatic(executor);
    }

    void initVerifier() throws ServerException {
        log.info("APIJSON Verifier Init");

        ApijsonUtils.buildAPIJSONVerifierStatic(verifierProperties, apijsonVerifierConfigurers.orderedStream().collect(Collectors.toList()));
    }

    void initRequestParaser() {
        log.info("APIJSON Request Parser Init");

        ApijsonParserProperties.Request request = parserProperties.getRequest();
        ApijsonUtils.buildAPIJSONParserStatic(request);
    }

    void initObjectParser() {
        log.info("APIJSON Object Parser Init");

        ApijsonParserProperties.Object object = parserProperties.getObject();
        ApijsonUtils.buildAPIJSONObjectParserStatic(object);
    }

    void initFunctionParser() {
        log.info("APIJSON Function Parser Init");

        ApijsonParserProperties.Function function = parserProperties.getFunction();
        ApijsonUtils.buildAPIJSONFunctionParserStatic(function, apijsonFunctionParserConfigurers.orderedStream().collect(Collectors.toList()));
    }
}
