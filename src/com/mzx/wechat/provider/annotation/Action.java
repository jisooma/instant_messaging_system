
package com.mzx.wechat.provider.annotation;


import com.mzx.wechat.controller.constant.RequestMethod;

import java.lang.annotation.*;

/**
 * @description 用于注解服务流程

 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Action {
    String path()default "";
    RequestMethod method();
}
