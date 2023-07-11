

package com.mzx.wechat.dao;

import com.mzx.wechat.dao.annotation.Delete;
import com.mzx.wechat.dao.annotation.Update;

/**
 * @description 负责消息记录CRUD
 */
public interface RecordDao extends BaseDao {
    String TABLE = "record";
    String ALL_FIELD = "user_id,message_id," + BASE_FIELD;


    /**
     * 一次性更新一个用户在一个聊天中所有记录的状态
     *
     * @param status 记录状态
     * @param userId 用户id
     * @param chatId 聊天id
     * @name updateStatusInChat
     * @notice none
     */
    @Update("update " + TABLE + " as r inner join message as m set r.status = ? where r.user_id = ? and r.message_id = m.id and m.chat_id = ?")
    void updateStatusInChat(Object status, Object userId ,Object chatId);


    /**
     * 一次性删除一个用户在一个聊天中所有记录的状态
     *
     * @param userId 用户id
     * @param chatId 聊天id
     * @name deleteAllRecordInChat
     * @notice none
     */
    @Update("delete r from  " + TABLE + " r inner join message  m on r.message_id = m.id where r.user_id = ? and m.chat_id = ?  " )
    void deleteAllRecordInChat(Object userId , Object chatId);


}
