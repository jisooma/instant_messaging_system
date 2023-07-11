

package com.mzx.wechat.model.po;

import com.alibaba.fastjson.JSONObject;
import com.mzx.wechat.dao.annotation.Table;
import com.mzx.wechat.model.po.abs.BaseEntity;

import java.math.BigInteger;

/**
 * @description 负责记录消息记录
 */
@Table(name = "record")
public class Record extends BaseEntity {
    private BigInteger userId;
    private BigInteger messageId;

    public BigInteger getUserId() {
        return userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public BigInteger getMessageId() {
        return messageId;
    }

    public void setMessageId(BigInteger messageId) {
        this.messageId = messageId;
    }


}
