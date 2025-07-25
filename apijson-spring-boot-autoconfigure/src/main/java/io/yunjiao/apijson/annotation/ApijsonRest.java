package io.yunjiao.apijson.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * apijson接口
 *
 * @author yangyunjiao
 */
@Target({TYPE})
@Retention(RUNTIME)
public @interface ApijsonRest {
}
