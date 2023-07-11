

package com.mzx.wechat.factory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @program wechat
 * @description 用于解析Dao接口中的sql语句，并执行
 */
public class ServiceProxyFactory implements InvocationHandler {

    private Object target;

    public Object getProxyInstance(Object target) {
        this.target =target;
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        return method.invoke(target, args);
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }
}
