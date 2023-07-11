
package com.mzx.wechat.service.constants;

/**
 * @description 聊天类型
 */
public enum ChatType {
    /**
     * 群聊
     */
    GROUP,
    /**
     * 私聊
     */
    FRIEND;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }

}
