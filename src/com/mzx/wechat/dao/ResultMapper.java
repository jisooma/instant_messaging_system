
package com.mzx.wechat.dao;


import java.sql.ResultSet;

/**
 * @program XHotel
 * @description 用于处理执行查询语句之后返回的结果集，将结果集映射为对象
 */
public interface ResultMapper {
    /**
     * 负责提供一个映射数据库查询结果集的方法
     * @name doMap
     * @param rs 需要映射的结果集
     * @return java.lang.Object
     * @notice none
     */
    Object doMap(ResultSet rs);
}
