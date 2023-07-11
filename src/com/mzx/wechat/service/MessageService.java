
package com.mzx.wechat.service;

import com.mzx.wechat.model.dto.ServiceResult;
import com.mzx.wechat.model.po.Message;

import java.math.BigInteger;

/**
 * @description 负责提供消息保存，聊天记录管理的服务
 */
public interface MessageService {

    /**
     * 将一条消息存入数据库，同时给聊天中的所有成员生成一份聊天记录
     *
     * @param message 要插入的消息
     * @name insertMessage
     * @notice none
     */
    void insertMessage(Message message);

    /**
     * 获取一个用户在一个聊天中的所有消息记录，不包括被删除的消息记录
     *
     * @param userId 用户id
     * @param chatId 聊天id
     * @param page   页数
     * @return
     */
    ServiceResult listAllMessage(Object userId, Object chatId, int page);

    /**
     * 获取一个用户在一个聊天中的所有未读的消息
     *
     * @param userId 用户id
     * @param chatId 聊天id
     * @param page   页数
     * @name listUnreadMessage
     * @notice none
     */
    ServiceResult listUnreadMessage(Object userId, Object chatId, int page);


    /**
     * 获取一个用户的所有未读的消息
     *
     * @param userId 用户id
     * @param page   页数
     * @notice none
     */
    ServiceResult listAllUnreadMessage(Object userId, int page);

    /**
     * 将一条消息从一个用户的消息记录中移除，并不会删除这条消息
     *
     * @param userId    用户id
     * @param messageId 要移除的消息记录id
     * @name removeMessage
     * @notice 本方法没有将消息删除，只是将该用户的对应这条消息的记录删除，</br>
     * 其他拥有这条消息的记录的用户依然可以访问这条消息
     */
    ServiceResult removeMessage(BigInteger userId, BigInteger messageId);


    /**
     * 删除一个用户在一个聊天中的所有记录
     *
     * @param userId 用户id
     * @param chatId 要移除的消息记录的聊天id
     * @name removeAllMessage
     * @notice 本方法没有将消息删除，只是将该用户的对应这条消息的记录删除，</br>
     * 其他拥有这条消息的记录的用户依然可以访问这条消息
     */
    ServiceResult removeAllMessage(BigInteger userId, BigInteger chatId);

    /**
     * 导出一个用户在一个聊天中的所有消息记录，返回文件名
     *
     * @param userId 用户id
     * @param chatId 聊天id
     */
    ServiceResult exportMessage(Object userId, Object chatId);


    /**
     * 撤回一条消息
     *
     * @param userId    用户id
     * @param messageId 消息id
     * @name drawMessage
     * @notice 这个方法会将消息删除
     */
    ServiceResult drawBackMessage(BigInteger userId, BigInteger messageId);




    /**
     * 将一个用户在一个聊天中收到的消息记录设置为已读
     *
     * @param userId 用户id
     * @param chatId 聊天id
     * @name setAlreadyRead
     * @notice none
     */
    void setAlreadyRead(Object userId, Object chatId);
}
