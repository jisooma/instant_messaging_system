
package com.mzx.wechat.dao;

import com.mzx.wechat.dao.annotation.Query;
import com.mzx.wechat.dao.annotation.Result;
import com.mzx.wechat.dao.annotation.ResultType;
import com.mzx.wechat.model.po.Message;

import java.math.BigInteger;
import java.util.List;

/**
 * @description 用于message表的CRUD
 */
public interface MessageDao extends BaseDao {

    String TABLE = "message";
    String ALL_FIELD = "sender_id,chat_id,content,type,time," + BASE_FIELD;



    /**
     * 通过发送id和聊天id查询一个消息
     *
     * @param senderId 发送者id
     * @param chatId   聊天id
     * @return 成员对象
     * @notice none
     */
    @Result(entity = Message.class, returns = ResultType.OBJECT)
    @Query(value = "select " + ALL_FIELD + " from " + TABLE + " where sender_id = ? and chat_id = ? and time = ? ")
    Message getMessageBySenderIdAndChatIdAndTime(BigInteger senderId, BigInteger chatId, Object time);


    /**
     * 通过用户id和聊天id并按时间顺序查询用户的所有消息记录
     *
     * @param userId 用户id
     * @param chatId 聊天id
     * @param limit  每页查询记录数
     * @param offset 起始记录数
     * @name listMessageByUserIdAndChatId
     * @notice none
     */
    @Result(entity = Message.class, returns = ResultType.LIST)
    @Query(value = "select m.id, m.sender_id, m.chat_id, m.content , m.type , m.time ,m.status , " +
            "m.gmt_create, m.gmt_modified from " + TABLE + " as m , record as r where m.id = r.message_id" +
            " and r.user_id = ? and m.chat_id = ? order by m.time limit ? offset ?  ")
    List<Message> listMessageByUserIdAndChatId(Object userId, Object chatId, int limit, int offset);


    /**
     * 通过用户id和聊天id并按时间顺序查询用户的所有消息记录
     *
     * @param userId 用户id
     * @param chatId 聊天id
     * @param limit  每页查询记录数
     * @param offset 起始记录数
     * @name listMessageByUserIdAndChatId
     * @notice none
     */
    @Result(entity = Message.class, returns = ResultType.LIST)
    @Query(value = "select m.id, m.sender_id, m.chat_id, m.content , m.type , m.time ,m.status , " +
            "m.gmt_create, m.gmt_modified from " + TABLE + " as m , record as r where m.id = r.message_id" +
            " and r.user_id = ? and m.chat_id = ? order by m.time desc limit ? offset ?  ")
    List<Message> listMessageByUserIdAndChatIdDesc(Object userId, Object chatId, int limit, int offset);


    /**
     * 通过用户id和聊天id并按时间顺序查询用户的所有消息记录并导出到文件
     *
     * @param userId   用户id
     * @param chatId   聊天id
     * @param fileName 导出文件名
     * @notice none
     */
    @Result(entity = Message.class, returns = ResultType.LIST)
    @Query(value = "select m.id, m.sender_id, m.chat_id, m.content , m.type , m.time ,m.status , " +
            "m.gmt_create, m.gmt_modified from " + TABLE + " as m , record as r where m.id = r.message_id" +
            " and r.user_id = ? and m.chat_id = ? order by m.time desc into outfile ? fields terminated by '," +
            "' optionally enclosed by '\"' lines terminated by '\\r\\n'; ")
    int messageToFile(Object userId, Object chatId, Object fileName);

    /**
     * 通过用户id和聊天id查询用户的所有未读消息记录
     *
     * @param userId 用户id
     * @param chatId 聊天id
     * @param limit  每页查询记录数
     * @param offset 起始记录数
     * @notice none
     */
    @Result(entity = Message.class, returns = ResultType.LIST)
    @Query(value = "select m.id, m.sender_id, m.chat_id, m.content , m.type , m.time ,m.status , " +
            "m.gmt_create, m.gmt_modified from " + TABLE + " as m , record as r where m.id = r.message_id" +
            " and r.user_id = ? and m.chat_id = ? and r.status = 0 order by m.time limit ? offset ?  ")
    List<Message> listUnreadMessage(Object userId, Object chatId, int limit, int offset);


    /**
     * 通过用户id查询用户的所有未读消息记录
     *
     * @param userId 用户id
     * @param limit  每页查询记录数
     * @param offset 起始记录数
     * @notice none
     */
    @Result(entity = Message.class, returns = ResultType.LIST)
    @Query(value = "select m.id, m.sender_id, m.chat_id, m.content , m.type , m.time ,m.status , " +
            "m.gmt_create, m.gmt_modified from " + TABLE + " as m , record as r where m.id = r.message_id" +
            " and r.user_id = ?  and r.status = 0  order by m.time desc  limit ? offset ? ")
    List<Message> listUnreadMessageByUserId(Object userId, int limit, int offset);

}
