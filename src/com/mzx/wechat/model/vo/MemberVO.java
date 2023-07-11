

package com.mzx.wechat.model.vo;

import com.mzx.wechat.model.po.Member;

/**
 * @description 群成员的视图层实体类
 */
public class MemberVO extends Member {
    private String name;
    private String signature;
    private String photo;
    public String getSignature() {
        return signature;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
