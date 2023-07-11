
package com.mzx.wechat.service;

import com.mzx.wechat.model.dto.ServiceResult;
import com.mzx.wechat.model.po.Friend;

/**
 * @description 负责好友相关服务
 */
public interface FriendService {
    /**
     * 添加好友关系
     *
     * @param friend 要添加的好友关系
     * @return
     * @name addFriend
     * @notice none
     */
    ServiceResult addFriend(Friend friend);



    /**
     * 返回一个用户的好友列表
     *
     * @param userId 用户id
     * @return
     * @name listFriend
     * @notice none
     */
    ServiceResult listFriend(Object userId);

    /**
     * 更新好友信息
     *
     * @param friend 朋友
     * @name updateFriend
     * @notice none
     */
    ServiceResult updateFriend(Friend friend);



    /**
     * 移除好友
     *
     * @param friend 要移除的好友关系
     * @return
     * @name removeFriend
     * @notice none
     */
    ServiceResult removeFriend(Friend friend);

    /**
     * 判断是否存在一条这样的朋友记录
     *
     * @param friend 判断该朋友关系是否是双向的
     * @name isFriend
     * @notice none
     */
    boolean isFriend(Friend friend);

    /**
     * 通过用户id和朋友id查询朋友关系
     *
     * @param uid      用户id
     * @param friendId 朋友id
     * @return
     * @name getByUidAndFriendId
     * @notice none
     */
    Friend getByUidAndFriendId(Object uid, Object friendId);

}
