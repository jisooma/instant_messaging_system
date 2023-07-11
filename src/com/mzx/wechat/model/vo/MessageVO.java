package com.mzx.wechat.model.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.mzx.wechat.model.po.Message;

import java.math.BigInteger;
import java.sql.Timestamp;

/**
 * @description 用于传输用户发送的消息
3
 */
public class MessageVO extends Message {
    @JSONField(name = "sender_name")
    private String senderName;
    @JSONField(name = "sender_photo")
    private String senderPhoto;



    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderPhoto() {
        return senderPhoto;
    }

    public void setSenderPhoto(String senderPhoto) {
        this.senderPhoto = senderPhoto;
    }
}
