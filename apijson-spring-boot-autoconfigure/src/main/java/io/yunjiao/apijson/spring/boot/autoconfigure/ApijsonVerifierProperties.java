package io.yunjiao.apijson.spring.boot.autoconfigure;

import io.yunjiao.apijson.util.ApijsonConsts;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 校验解析器配置， 参考{@link apijson.framework.APIJSONVerifier}
 *
 * @author yangyunjiao
 */
@Data
@ConfigurationProperties(prefix = ApijsonConsts.PROPERTY_PREFIX_APIJSON_VERIFIER)
public class ApijsonVerifierProperties {
    private boolean enableVerifyColumn = true;

    private boolean enableApijsonRouter = false;

    /**
     * 为 PUT, DELETE 强制要求必须有 id/id{}/id{}@ 条件
     */
    private boolean isUpdateMustHaveIdCondition = true;

    /**
     * 开启校验请求角色权限
     */
    private boolean enableVerifyRole = true;

    /**
     * 开启校验请求传参内容
     */
    private boolean enableVerifyContent = true;
}
