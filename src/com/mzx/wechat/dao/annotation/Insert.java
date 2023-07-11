

package com.mzx.wechat.dao.annotation;

import java.lang.annotation.*;

/**

 * @program wechat
 * @description 用于注解插入语句

 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Insert {
    String value()default "";
}
