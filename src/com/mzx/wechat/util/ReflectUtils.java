

package com.mzx.wechat.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * @program XHotel
 * @description 用于反射的工具
 */
public class ReflectUtils {


    public static LinkedList<Method> getMethods(Object obj) {
        return getMethods(obj.getClass());
    }

    public static LinkedList<Method> getMethods(Class clazz) {
        LinkedList<Method> methods = new LinkedList<>();
        for (Class cla = clazz; cla != Object.class; cla = cla.getSuperclass()) {
            methods.addAll(Arrays.asList(cla.getDeclaredMethods()));
        }
        return methods;
    }

    public static LinkedList<Field> getFields(Object obj) {
        return getFields(obj.getClass());
    }

    public static LinkedList<Field> getFields(Class clazz) {
        LinkedList<Field> fields = new LinkedList<>();
        for (Class cla = clazz; cla != Object.class; cla = cla.getSuperclass()) {
            fields.addAll(Arrays.asList(cla.getDeclaredFields()));
        }
        return fields;
    }


}
