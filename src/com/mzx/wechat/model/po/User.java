
package com.mzx.wechat.model.po;

import com.mzx.wechat.dao.annotation.Field;
import com.mzx.wechat.dao.annotation.Table;
import com.mzx.wechat.model.po.abs.BaseEntity;

/**
 * @program wechat
 * @description 用户实体类
 */
//@Entity表明该类是实体类，并且使用默认的orm规则，即class名即数据库表中表名，class字段名即表中的字段名。
// 如果class名和数据库表中名字不一致就用@Table，如果class字段名和表中的字段名不一直就用@Column
@Table(name = "user")
public class User extends BaseEntity {
    @Field(name = "email")
    private String email;
    @Field(name = "wechat_id")
    private String wechatId;
    @Field(name = "phone")
    private String phone;
    @Field(name = "password")
    private String password;
    @Field(name = "gender")
    private String gender;
    @Field(name = "signature")
    private String signature;
    @Field(name = "name")
    private String name;
    @Field(name = "photo")
    private String photo;
    @Field(name = "chat_background")
    private String chatBackground;
    @Field(name = "location")
    private String location;
    @Field(name = "online_status")
    private String onlineStatus;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWechatId() {
        return wechatId;
    }

    public void setWechatId(String wechatId) {
        this.wechatId = wechatId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getChatBackground() {
        return chatBackground;
    }

    public void setChatBackground(String chatBackground) {
        this.chatBackground = chatBackground;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(String onlineStatus) {
        this.onlineStatus = onlineStatus;
    }
}
