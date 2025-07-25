package io.yunjiao.apijson.spring.boot.autoconfigure.condition;

import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 泛型枚举配置条件抽象类
 *
 * @author yangyunjiao
 * @param <E> 枚举类型
 */
public abstract class EnumPropertyCondition<E extends Enum<E>> extends SpringBootCondition {
    private final E expectedValue;

    private final Class<E> enumClass;

    private final String propertyKey;

    protected EnumPropertyCondition(E expectedValue, Class<E> enumClass, String propertyKey) {
        this.expectedValue = expectedValue;
        this.enumClass = enumClass;
        this.propertyKey = propertyKey;
    }

    /**
     * 获取条件名称（用于日志和报告）
     */
    protected String getConditionName() {
        return getClass().getSimpleName();
    }

    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context,
                                            AnnotatedTypeMetadata metadata) {

        ConditionMessage.Builder message = ConditionMessage.forCondition(
                getConditionName(), "(" + expectedValue + ")"
        );

        Environment env = context.getEnvironment();
        String rawValue = env.getProperty(propertyKey);

        // 1. 检查属性是否存在
        if (!StringUtils.hasText(rawValue)) {
            return ConditionOutcome.noMatch(
                    message.because("未配置属性 '" + propertyKey + "'")
            );
        }

        // 2. 转换为枚举
        E actualValue = parseEnumValue(rawValue);
        if (actualValue == null) {
            String validValues = Arrays.stream(enumClass.getEnumConstants())
                    .map(Enum::name)
                    .collect(Collectors.joining(", "));
            return ConditionOutcome.noMatch(
                    message.because("'" + rawValue + "' 不是有效值。有效值为: " + validValues)
            );
        }

        // 3. 比较枚举值
        if (Objects.equals(actualValue, expectedValue)) {
            return ConditionOutcome.match(
                    message.because("配置值 '" + actualValue + "' 匹配期望值")
            );
        }

        return ConditionOutcome.noMatch(
                message.because("配置值 '" + actualValue + "' 不匹配期望值 '" + expectedValue + "'")
        );
    }

    /**
     * 解析字符串为枚举值
     */
    private E parseEnumValue(String value) {
        if (!StringUtils.hasText(value)) {
            return null;
        }

        String normalized = value.trim().toLowerCase();
        try {
            return Enum.valueOf(enumClass, normalized);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
