package io.yunjiao.apijson;

import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * 测试工具
 *
 * @author yangyunjiao
 */
public interface Utils {
    static ConditionContext createMockContext(Environment env) {
        ConditionContext context = mock(ConditionContext.class);
        when(context.getEnvironment()).thenReturn(env);
        return context;
    }
}
