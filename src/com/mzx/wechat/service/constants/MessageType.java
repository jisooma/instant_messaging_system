

package com.mzx.wechat.service.constants;

/**a>
 * @description 用于枚举消息的类型
 */
public enum MessageType {
    /**
     * 用户之间的文本消息
     */
    USER,
    /**
     * 图片消息
     */
    IMG,
    /**
     * 文件消息
     */
    FILE,
    /**
     * 加好友通知
     */
    FRIEND;
    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }

    }
