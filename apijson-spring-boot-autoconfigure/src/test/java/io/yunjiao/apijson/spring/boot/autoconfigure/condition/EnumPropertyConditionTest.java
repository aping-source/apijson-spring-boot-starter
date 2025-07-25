package io.yunjiao.apijson.spring.boot.autoconfigure.condition;

import io.yunjiao.apijson.Utils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.mock.env.MockEnvironment;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link EnumPropertyCondition} 单元测试用例
 *
 * @author yangyunjiao
 */
@ExtendWith(MockitoExtension.class)
public class EnumPropertyConditionTest {
    public static final  String PROPERTY_NAME = "spring.gender";

    @Mock
    private AnnotatedTypeMetadata metadata;

    @Test
    void shouldMatchWhenPropertyMatchesExpectedType() {
        // 准备测试环境
        MockEnvironment env = new MockEnvironment();
        env.setProperty(PROPERTY_NAME, "female");

        ConditionContext context = Utils.createMockContext(env);

        // 创建条件实例
        GenderCondition condition =
                new GenderCondition(Gender.female);

        // 执行测试
        ConditionOutcome outcome = condition.getMatchOutcome(context, metadata);

        // 验证结果
        assertThat(outcome.isMatch()).isTrue();
        assertThat(outcome.getMessage()).contains("配置值 'female' 匹配期望值");
    }

    @Test
    void shouldNotMatchWhenPropertyIsDifferent() {
        MockEnvironment env = new MockEnvironment();
        env.setProperty(PROPERTY_NAME, "male");

        ConditionContext context = Utils.createMockContext(env);
        GenderCondition condition =
                new GenderCondition(Gender.female);

        ConditionOutcome outcome = condition.getMatchOutcome(context, metadata);

        assertThat(outcome.isMatch()).isFalse();
        assertThat(outcome.getMessage())
                .contains("配置值 'male' 不匹配期望值 'female'");
    }

    @Test
    void shouldNotMatchWhenPropertyIsMissing() {
        MockEnvironment env = new MockEnvironment(); // 无属性

        ConditionContext context = Utils.createMockContext(env);
        GenderCondition condition =
                new GenderCondition(Gender.female);

        ConditionOutcome outcome = condition.getMatchOutcome(context, metadata);

        assertThat(outcome.isMatch()).isFalse();
        assertThat(outcome.getMessage())
                .contains("未配置属性 '" + PROPERTY_NAME + "'");
    }

    @Test
    void shouldNotMatchWhenPropertyIsInvalid() {
        MockEnvironment env = new MockEnvironment();
        env.setProperty(PROPERTY_NAME, "INVALID_TYPE");

        ConditionContext context = Utils.createMockContext(env);
        GenderCondition condition =
                new GenderCondition(Gender.female);

        ConditionOutcome outcome = condition.getMatchOutcome(context, metadata);

        assertThat(outcome.isMatch()).isFalse();
        assertThat(outcome.getMessage())
                .contains("'INVALID_TYPE' 不是有效值")
                .contains("male, female, unknown");
    }

    @Test
    void shouldBeCaseInsensitive() {
        MockEnvironment env = new MockEnvironment();
        env.setProperty(PROPERTY_NAME, "FEMALE");

        ConditionContext context = Utils.createMockContext(env);
        GenderCondition condition =
                new GenderCondition(Gender.female); // 小写

        ConditionOutcome outcome = condition.getMatchOutcome(context, metadata);

        assertThat(outcome.isMatch()).isTrue();
    }


    @Test
    void shouldHandleEmptyPropertyValue() {
        MockEnvironment env = new MockEnvironment();
        env.setProperty(PROPERTY_NAME, ""); // 空字符串

        ConditionContext context = Utils.createMockContext(env);
        GenderCondition condition =
                new GenderCondition(Gender.female);

        ConditionOutcome outcome = condition.getMatchOutcome(context, metadata);

        assertThat(outcome.isMatch()).isFalse();
        assertThat(outcome.getMessage())
                .contains("未配置属性 '" + PROPERTY_NAME + "'");
    }


    public enum Gender {
        // 必须小写定义
        male, female, unknown
    }

    public static class GenderCondition extends EnumPropertyCondition<Gender> {
        protected GenderCondition(Gender expectedValue) {
            super(expectedValue, Gender.class, PROPERTY_NAME);
        }
    }
}
