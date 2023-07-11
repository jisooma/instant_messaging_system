

package com.mzx.wechat.provider.annotation;

import java.lang.annotation.*;

/**

 * @description 用于注解ActionProvider的url路径

 */
@Documented
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
public @interface ActionProvider {
    String path () default "";
}
