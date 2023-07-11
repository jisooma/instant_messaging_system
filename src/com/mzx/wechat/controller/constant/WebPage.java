
package com.mzx.wechat.controller.constant;

/**
 * 界面的地址常量
 *

 * @program XHotel
 * @description

 */
public enum WebPage {


    /**
     * 注册界面
     */
    REGISTER_JSP,
    /**
     * 网站首页
     */
    INDEX_JSP,
    /**
     * 错误页面
     */
    ERROR_JSP,
    /**
     * 房间页面
     */
    ROOM_JSP,
    /**
     * 登陆界面
     */
    LOGIN_JSP,
    /**
     * 用户界面
     */
    USER_JSP,
    /**
     * 订单界面
     */
    ORDER_JSP,
    /**
     * 成功页面
     */
    SUCCESS_JSP,
    /**
     * 评论界面
     */
    REMARK_JSP,
    /**
     * 相册页面
     */
    PICTRUES_JSP;

    @Override
    public String toString() {
        return "/"+super.toString().toLowerCase().replaceAll("_", ".");
    }
}