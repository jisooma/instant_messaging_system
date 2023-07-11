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

package com.mzx.wechat.model.po.abs;

import com.alibaba.fastjson.JSONObject;
import com.mzx.wechat.dao.annotation.Field;

import java.math.BigInteger;
import java.util.Date;

/**
 * @program wechat
 * @description 所有数据库记录的父类
 */
public abstract class BaseEntity {

    @Field(name = "id")
    private BigInteger id;
    @Field(name = "status")
    private Integer status;
    @Field(name = "gmt_create")
    private Date gmtCreate;
    @Field(name = "gmt_modified")
    private Date gmtModified;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * 所有的po层实体类都继承此类，在这里一次性重写toString方法，使用json格式输出
     *
     * @return
     * @name toString
     * @notice none
     * @author <a href="mailto:kobe524348@gmail.com">黄钰朝</a>
     * @date 2019/5/7
     */
    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

}
