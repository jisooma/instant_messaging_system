
package com.mzx.wechat.dao;

import com.mzx.wechat.dao.annotation.Query;
import com.mzx.wechat.dao.annotation.Result;
import com.mzx.wechat.dao.annotation.ResultType;
import com.mzx.wechat.model.po.Friend;
import com.mzx.wechat.model.po.Member;

import java.math.BigInteger;
import java.util.List;

/**
 * @program wechat
 * @description 负责朋友表CRUD
 */
public interface FriendDao extends BaseDao {

    String ALL_FIELD = "user_id,friend_id,chat_id,photo,alias,description," + BASE_FIELD;
    String TABLE = "friend";

    /**
     * 通过用户id和朋友id获取一条好友记录
     *
     * @param userId   用户id
     * @param friendId 朋友id
     * @return
     * @name getFriendByUIDAndFriendId
     * @notice none
     */
    @Result(entity = Friend.class, returns = ResultType.OBJECT)
    @Query(value = "select " + ALL_FIELD + " from " + TABLE + " where user_id = ? and friend_id = ? ")
    Friend getFriendByUIDAndFriendId(Object userId, Object friendId);


    /**
     * 通过用户id查询好友列表
     *
     * @param userId 用户id
     * @return
     * @name listByUserId
     * @notice none
     */
    @Result(entity = Friend.class, returns = ResultType.LIST)
    @Query(value = "select f.id,f.user_id,f.friend_id,f.chat_id,f.alias,f.description,u.photo as photo,f.status,f.gmt_create,f.gmt_modified " +
            "from " + TABLE + " as f,user as u where f.user_id = ? and u.id = f.friend_id ")
    List listByUserId(Object userId);


    /**
     * 通过朋友关系id查询一个朋友关系
     *
     * @param id 朋友id
     * @name getFriendById
     * @notice none
     */
    @Result(entity = Friend.class, returns = ResultType.OBJECT)
    @Query(value = "select " + ALL_FIELD + " from " + TABLE + " where id = ? ")
    Friend getFriendById(Object id);

}
