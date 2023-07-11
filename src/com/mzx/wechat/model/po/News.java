

package com.mzx.wechat.model.po;

import com.alibaba.fastjson.annotation.JSONField;
import com.mzx.wechat.dao.annotation.Table;
import com.mzx.wechat.model.po.abs.BaseEntity;

import java.math.BigInteger;

/**
 * @description 动态表的实体类
 */
@Table(name = "news")
public class News extends BaseEntity {
    @JSONField(name = "user_id")
    private BigInteger userId;
    @JSONField(name = "moment_Id")
    private BigInteger momentId;
    private Boolean loved;
    private Boolean shared;
    private Boolean viewed;
    private Boolean collected;

    public BigInteger getUserId() {
        return userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public BigInteger getMomentId() {
        return momentId;
    }

    public void setMomentId(BigInteger momentId) {
        this.momentId = momentId;
    }

    public Boolean getLoved() {
        return loved;
    }

    public void setLoved(Boolean loved) {
        this.loved = loved;
    }

    public Boolean getShared() {
        return shared;
    }

    public void setShared(Boolean shared) {
        this.shared = shared;
    }

    public Boolean getViewed() {
        return viewed;
    }

    public void setViewed(Boolean viewed) {
        this.viewed = viewed;
    }

    public Boolean getCollected() {
        return collected;
    }

    public void setCollected(Boolean collected) {
        this.collected = collected;
    }
}
