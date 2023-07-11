
package com.mzx.wechat.dao.annotation;

import java.lang.annotation.*;
//包 java.lang.annotation 中包含所有定义自定义注解所需用到的原注解和接口。
  //  如接口 java.lang.annotation.Annotation 是所有注解继承的接口,并且是自动继承，不需要定义时指定，类似于所有类都自动继承Object。
/**

 * @program wechat
 * @description 用于注解删除语句

 */
//  @Documented 将此注解包含在 javadoc 中 ，它代表着此注解会被javadoc工具提取成文档。
//  在doc文档中的内容会因为此注解的信息内容不同而不同。相当与@see,@param 等。
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Delete {
    String value()default "";
}
