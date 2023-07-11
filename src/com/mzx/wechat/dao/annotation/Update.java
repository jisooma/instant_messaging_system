
package com.mzx.wechat.dao.annotation;

import java.lang.annotation.*;

/**
 * @description 用于注解更新语句
 * @program wechat
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Update {
    String value()default "";
}
