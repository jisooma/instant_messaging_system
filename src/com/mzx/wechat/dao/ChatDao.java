

package com.mzx.wechat.dao;

import com.mzx.wechat.dao.annotation.Query;
import com.mzx.wechat.dao.annotation.Result;
import com.mzx.wechat.dao.annotation.ResultType;
import com.mzx.wechat.model.po.Chat;

import java.util.List;

/**
 * @description 负责聊天表的CRUD
 */
public interface ChatDao extends BaseDao {

    String TABLE = "chat";
    String ALL_FIELD = "number,owner_id,type,name,member,photo," + BASE_FIELD;

    /**
     * 通过聊天id查询一个聊天
     *
     * @param id 聊天id
     * @return 聊天对象
     * @name getChatById
     * @notice none
     */
    @Result(entity = Chat.class, returns = ResultType.OBJECT)
    @Query(value = "select " + ALL_FIELD + " from " + TABLE + " where id = ? ")
    Chat getChatById(Object id);

    /**
     * 通过用户id查询该用户所处的所有聊天
     *
     * @param userId 用户id
     * @return 该用户加入的聊天集合
     * @name listByUserId
     * @notice none
     */
    @Result(entity = Chat.class, returns = ResultType.LIST)
    @Query(value = "select c.id,c.number,c.owner_id,c.name,c.type,c.member,c.photo,c.status,c.gmt_create,c.gmt_modified " +
            "from " + TABLE + " as c,member as m where c.id = m.chat_id and m.user_id = ?")
    List<Chat> listByUserId(Object userId);


    /**
     * 将对方的头像作为聊天头像，将对方的昵称作为聊天名称<br/>
     * 查询一个聊天信息
     *
     * @param chatId 聊天id
     * @param userId 用户id
     * @return
     * @name toFriendChat
     * @notice none
     */
    @Result(entity = Chat.class, returns = ResultType.OBJECT)
    @Query(value = "select c.id,c.number,c.owner_id,u.name as name,c.member,u.photo as photo,c.type,c.status,c.gmt_create,c.gmt_modified " +
            "from " + TABLE + " as c,member as m,user as u where c.id = ? and m.chat_id = c.id and u.id = m.user_id and u.id <> ?")
    Chat toFriendChat(Object chatId, Object userId);


    /**
     * 通过聊天编号查询一个聊天
     *
     * @param number 聊天编号
     * @return
     * @name getByChatNumber
     * @notice none
     */
    @Result(entity = Chat.class,returns = ResultType.OBJECT)
    @Query(value = "select "+ALL_FIELD+" from "+TABLE+" where number = ? ")
    Chat getByChatNumber(Object number);
}
