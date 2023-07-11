

package com.mzx.wechat.controller.annotation;

import java.lang.annotation.*;

/**
 * @description 用于注解ActionProvider的url路径
 */
@Documented
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
public @interface ControllerConfig {
    String path() default "";
}
