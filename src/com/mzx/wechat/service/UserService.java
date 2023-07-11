

package com.mzx.wechat.service;

import com.mzx.wechat.model.dto.ServiceResult;
import com.mzx.wechat.model.po.User;

import java.math.BigInteger;

/**
 * @program wechat
 * @description 负责提供用户服务
 */
public interface UserService {
    /**
     * 检查注册用户的信息是否有效
     *
     * @param user 用户对象
     * @return 返回传入时的对象
     */
    ServiceResult checkRegister(User user);

    /**
     * 添加一个用户账号
     *
     * @param user 用户对象
     * @return 返回传入的用户的对象
     */
    ServiceResult insertUser(User user);

    /**
     * 校验用户的密码
     *
     * @param user 用户对象
     * @return 返回传入的用户对象
     */
    ServiceResult checkPassword(User user);

    /**
     * 校验用户名（微信号），是否合法，是否已被占用
     *
     * @param wechatId 微信号
     * @return 返回传入的用户名
     */
    ServiceResult checkWechatId(String wechatId);

    /**
     * 通过用户id获取用户个人信息
     *
     * @param id 用户id
     * @return 返回用户的个人信息
     */
    ServiceResult getUser(Object id);


    /**
     * 更新用户的个人信息,不包括密码，邮箱
     *
     * @param user 用户对象
     * @return 返回传入的用户对象，如果由密码信息/邮箱信息，将被清空
     */
    ServiceResult updateUser(User user);

    /**
     * 更新用户的个人密码
     *
     * @param oldPwd 旧密码
     * @param newPwd 新密码
     * @param userId 用户id
     * @return 返回传入的用户对象，如果由密码信息/邮箱信息，将被清空
     */
    ServiceResult updatePwd(String oldPwd, String newPwd , BigInteger userId);

    /**
     * 返回昵称与传入参数相似的用户列表
     * @param name 用户昵称
     * @return
     * @name listUserLikeName
     * @notice none
     */
    ServiceResult listUserLikeName(String name);



}
