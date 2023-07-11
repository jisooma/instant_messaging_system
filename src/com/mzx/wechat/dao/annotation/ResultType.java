

package com.mzx.wechat.dao.annotation;

/**
 * @program wechat
 * @description 用于枚举SQL语句返回值类型
 */
public  enum ResultType{
    /**
     * 返回值为对象类型
     */
    OBJECT,
    /**
     * 返回值为链表类型
     */
    LIST,
    /**
     * 返回值为值类型
     */
    VALUE;
}