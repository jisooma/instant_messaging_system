
package com.mzx.wechat.dao.annotation;

import java.lang.annotation.*;


/**
 * @program wechat
 * @description 用于注解表名
 */
@Documented
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
public @interface Table {
        String name() ;
}
