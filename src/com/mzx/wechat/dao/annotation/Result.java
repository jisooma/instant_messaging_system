
package com.mzx.wechat.dao.annotation;

import com.mzx.wechat.model.po.abs.BaseEntity;

import java.lang.annotation.*;

/**
 *
 * @description 用于注解SQL语句的返回结果类型
 * @program wechat

 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Result {
    Class<? extends BaseEntity> entity();
    ResultType returns ()default ResultType.OBJECT;
}
