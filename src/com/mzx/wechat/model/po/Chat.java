
package com.mzx.wechat.model.po;

import com.alibaba.fastjson.annotation.JSONField;
import com.mzx.wechat.dao.annotation.Table;
import com.mzx.wechat.model.po.abs.BaseEntity;

import java.math.BigInteger;

/**
 * @description 对应数据库chat表
 */
@Table(name = "chat")
public class Chat extends BaseEntity {
    private String number;
    @JSONField(name = "owner_id")
    private BigInteger ownerId;
    private String type;
    private String name;
    private Integer member;
    private String photo;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public BigInteger getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(BigInteger ownerId) {
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMember() {
        return member;
    }

    public void setMember(Integer member) {
        this.member = member;
    }
}
