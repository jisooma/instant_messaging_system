

package com.mzx.wechat.dao;

import com.mzx.wechat.dao.annotation.*;

/**
 * @program wechat
 * @description 提供Dao的基础方法接口
 */
public interface BaseDao {

    String BASE_FIELD = "id,status,gmt_create,gmt_modified";

    /*
     **************************************************************
     *          负责数据库insert,update,delete等功能
     **************************************************************
     */


    /**
     * 把一个对象插入一张表
     *
     * @param obj 要插入的对象
     * @name insert
     * @notice none
     */
    @Insert()
    int insert(Object obj);



    /**
     * 根据传入的表名和id，从该表中更新一条记录
     *
     * @param obj 要更新的记录对应的实体类对象
     * @name update
     * @notice 请注意此方法默认更新该对象中所有不为null的属性到数据库<br>
     * 如果不希望更新某些字段，请将其设置为null
     */
    @Update()
    int update(Object obj);

    /**
     * 将一个对象从表中删除
     *
     * @param obj 要删除的对象
     * @name delete
     * @notice none
     */
    @Delete()
    int delete(Object obj);

}
