
package com.mzx.wechat.model.builder;

import com.mzx.wechat.model.vo.MessageVO;

import java.math.BigInteger;
import java.sql.Timestamp;

/**
 * @description 负责MessageVO的建造
 */
public class MessageVOBuilder {
    private MessageVO messageVo;

    public MessageVOBuilder() {
        this.messageVo = new MessageVO();
    }

    public MessageVO build(){
        return this.messageVo;
    }

    public MessageVOBuilder setSenderName(String senderName) {
        this.messageVo.setSenderName(senderName);
        return this;
    }
    public MessageVOBuilder setSenderId(BigInteger senderId){
        this.messageVo.setSenderId(senderId);
        return this;
    }
    public MessageVOBuilder setSenderPhoto(String senderPhoto){
        this.messageVo.setSenderPhoto(senderPhoto);
        return this;
    }
    public MessageVOBuilder setChatId(BigInteger chatId){
        this.messageVo.setChatId(chatId);
        return this;
    }
    public MessageVOBuilder setContent(String content){
        this.messageVo.setContent(content);
        return this;
    }
    public MessageVOBuilder setTime(Timestamp time){
        this.messageVo.setTime(time);
        return this;
    }
    public MessageVOBuilder setType(String type){
        this.messageVo.setType(type);
        return this;
    }
    public MessageVOBuilder setId(BigInteger id){
        this.messageVo.setId(id);
        return this;
    }
}
