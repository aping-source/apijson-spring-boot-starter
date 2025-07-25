package io.yunjiao.apijson.spring.boot.autoconfigure.condition;

import io.yunjiao.apijson.spring.boot.autoconfigure.ApijsonProperties;
import io.yunjiao.apijson.util.ApijsonConsts;

/**
 * 应用程序类型条件
 *
 * @author yangyunjiao
 */
public class ApllicationCondition extends EnumPropertyCondition<ApijsonProperties.Application> {
    protected ApllicationCondition(ApijsonProperties.Application expectedValue) {
        super(expectedValue, ApijsonProperties.Application.class, ApijsonConsts.PROPERTY_PREFIX_APIJSON_APPLICATION);
    }

    public static class OnFastjson2 extends ApllicationCondition {
        public OnFastjson2() {
            super(ApijsonProperties.Application.fastjson2);
        }
    }

    public static class OnGson extends ApllicationCondition {
        public OnGson() {
            super(ApijsonProperties.Application.gson);
        }
    }
}
