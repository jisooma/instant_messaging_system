
package com.mzx.wechat.dao;

import com.mzx.wechat.dao.annotation.Query;
import com.mzx.wechat.dao.annotation.Result;
import com.mzx.wechat.dao.annotation.ResultType;
import com.mzx.wechat.model.po.User;

import java.util.List;

/**
 * @program wechat
 * @description 负责User类CRUD
 */
public interface UserDao extends BaseDao {
    String TABLE = "user";
    String ALL_FIELD = "email,wechat_id,phone,password,gender,signature,name,photo"
            + ",chat_background,location,online_status," + BASE_FIELD;


    /**
     * 通过id查询一个用户
     *
     * @param id
     * @return 用户对象
     * @name getUserById
     * @notice none
     */
    @Result(entity = User.class, returns = ResultType.OBJECT)
    @Query(value = "select " + ALL_FIELD + " from " + TABLE + " where id = ? ")
    User getUserById(Object id);


    /**
     * 通过邮箱查询一个用户
     *
     * @param email 账户邮箱
     * @return 用户对象
     * @name getUserByEmail
     * @notice none
     */
    @Result(entity = User.class, returns = ResultType.OBJECT)
    @Query(value = "select " + ALL_FIELD + " from " + TABLE + " where email = ? ")
    User getUserByEmail(String email);


    /**
     * 通过用户名查询一个用户
     *
     * @param wechatId 微信号
     * @return 用户对象
     * @name getUserByWechatId
     * @notice none
     */
    @Result(entity = User.class, returns = ResultType.OBJECT)
    @Query(value = "select " + ALL_FIELD + " from " + TABLE + " where wechat_id = ? ")
    User getUserByWechatId(String wechatId);

    /**
     * @param name 用户昵称
     * @return
     * @name listByName
     * @notice none
     */
    @Result(entity = User.class, returns = ResultType.LIST)
    @Query(value = "select " + ALL_FIELD + " from " + TABLE + " where name = ?")
    List<User> listByName(String name);


    /**
     * 通过关键词模糊查询用户昵称相近的用户
     *
     * @param keyWord 关键词
     * @return
     * @name listLike
     * @notice 注意本方法本身不带有模糊查询功能，必须在传入参数中带有模糊条件
     */
    @Result(entity = User.class, returns = ResultType.LIST)
    @Query(value = "select " + ALL_FIELD + " from " + TABLE + " where name like ? ")
    List<User> listLikeName(String keyWord);


}
