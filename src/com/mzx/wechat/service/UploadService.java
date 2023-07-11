
package com.mzx.wechat.service;

import com.mzx.wechat.model.dto.ServiceResult;

import javax.servlet.http.Part;

/**

 * @description 负责提供文件上传服务
 */
public interface UploadService {

    /**
     * 负责将文件写入文件，并将数据库表对应的记录上的photo属性值修改为文件名
     *
     * @param part      文件
     * @param id        记录id
     * @param tableName 表名
     * @return
     * @name uploadPhoto
     * @notice none
     */
    ServiceResult uploadPhoto(Part part, Object id, String tableName);

    /**
     * 负责将文件写入文件，并将数据库表对应聊天背景属性值修改为文件名
     *
     * @param part      文件
     * @param id        记录id
     * @return
     * @name uploadBackground
     * @notice none
     */
    ServiceResult uploadBackground(Part part, Object id);


    /**
     * 负责将文件写入文件，并返回文件名
     *
     * @param part      文件
     * @name uploadFile
     * @notice none
     */
    ServiceResult uploadFile(Part part);


}
