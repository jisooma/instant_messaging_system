/*
 * Copyright (c) 2019.  黄钰朝
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mzx.wechat.dao.annotation;

import java.lang.annotation.*;

/**

 * @program wechat
 * @description 用于注解查询语句

 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
//注解@Target也是用来修饰注解的元注解，它有一个属性ElementType也是枚举类型，
//如@Target(ElementType.METHOD) 修饰的注解表示该注解只能用来修饰在方法上。
@Target(ElementType.METHOD)
public @interface Query {
    //如果注解中有一个属性名字叫value,则在应用时可以省略属性名字不写。
    String value()default "";
}
