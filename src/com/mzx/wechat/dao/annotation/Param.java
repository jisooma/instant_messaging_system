
package com.mzx.wechat.dao.annotation;

import java.lang.annotation.*;

/**

 * @description 用于注解SQL语句的返回结果类型
 * @program wechat

 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Param {
    String value();
}
