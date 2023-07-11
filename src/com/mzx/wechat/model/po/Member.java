
package com.mzx.wechat.model.po;

import com.alibaba.fastjson.annotation.JSONField;
import com.mzx.wechat.dao.annotation.Table;
import com.mzx.wechat.model.po.abs.BaseEntity;

import java.math.BigInteger;

/**
 * @description 用户和群聊的中间表
 */
@Table(name = "member")
public class Member extends BaseEntity {
    @JSONField(name = "user_id")
    private BigInteger userId;
    @JSONField(name = "chat_id")
    private BigInteger chatId;
    @JSONField(name = "group_alias")
    private String groupAlias;
    private String apply;
    private String background;
    private Integer level;

    public String getApply() {
        return apply;
    }

    public void setApply(String apply) {
        this.apply = apply;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public BigInteger getUserId() {
        return userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public BigInteger getChatId() {
        return chatId;
    }

    public void setChatId(BigInteger chatId) {
        this.chatId = chatId;
    }

    public String getGroupAlias() {
        return groupAlias;
    }

    public void setGroupAlias(String groupAlias) {
        this.groupAlias = groupAlias;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }
}
