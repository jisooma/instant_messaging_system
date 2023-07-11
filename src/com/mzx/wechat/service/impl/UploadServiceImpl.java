
package com.mzx.wechat.service.impl;

import com.mzx.wechat.dao.MomentDao;
import com.mzx.wechat.dao.UserDao;
import com.mzx.wechat.exception.DaoException;
import com.mzx.wechat.factory.DaoProxyFactory;
import com.mzx.wechat.model.dto.ServiceResult;
import com.mzx.wechat.model.po.Moment;
import com.mzx.wechat.model.po.User;
import com.mzx.wechat.service.UploadService;
import com.mzx.wechat.service.constants.Status;

import javax.servlet.http.Part;
import java.io.IOException;
import java.math.BigInteger;

import static com.mzx.wechat.service.constants.ServiceMessage.*;
import static com.mzx.wechat.util.UploadUtils.toFileName;
import static com.mzx.wechat.util.UploadUtils.toPhotoName;

/**
 * 负责文件上传服务
 * @description
 */
public class UploadServiceImpl implements UploadService {

    private final UserDao userDao = (UserDao) DaoProxyFactory.getInstance().getProxyInstance(UserDao.class);
   private final MomentDao momentDao = (MomentDao) DaoProxyFactory.getInstance().getProxyInstance(MomentDao.class);
//
//    /**
//     * 负责将文件写入文件，并将数据库表对应的记录上的photo属性值修改为文件名
//     *
//     * @param part      文件
//     * @param id        记录id
//     * @param tableName 表名
//     * @return
//     * @name uploadPhoto
//     * @notice none
//     */
//    @Override
    public ServiceResult uploadPhoto(Part part, Object id, String tableName) {
        String fileName;
        try {
            fileName = toPhotoName(part);
            if ("user".equalsIgnoreCase(tableName)) {
                User user = new User();
                user.setId(new BigInteger(String.valueOf(id)));
                user.setPhoto(fileName);
                if (userDao.update(user) != 1) {
                    return new ServiceResult(Status.ERROR, UPDATE_USER_FAILED.message, user);
                }
            } else if ("moment".equalsIgnoreCase(tableName)) {
                Moment moment = new Moment();
                moment.setId(new BigInteger(String.valueOf(id)));
                moment.setPhoto(fileName);
                if (momentDao.update(moment) != 1) {
                    return new ServiceResult(Status.ERROR, DATABASE_ERROR.message, moment);
                }
            } else {
                return new ServiceResult(Status.ERROR, UNSUPPROT_TABLE.message, tableName);
            }
        } catch (DaoException e) {
            e.printStackTrace();
            return new ServiceResult(Status.ERROR, DATABASE_ERROR.message, id);
        } catch (IOException e) {
            e.printStackTrace();
            return new ServiceResult(Status.ERROR, UPLOAD_FAILED.message, id);
        }
        return new ServiceResult(Status.SUCCESS, UPDATE_INFO_SUCCESS.message, fileName);
   }
    @Override
    public ServiceResult uploadBackground(Part part, Object id) {
        String fileName;
        try {
            fileName = toPhotoName(part);
            User user = new User();
            user.setId(new BigInteger(String.valueOf(id)));
            user.setChatBackground(fileName);
            if (userDao.update(user) != 1) {
                return new ServiceResult(Status.ERROR, UPDATE_USER_FAILED.message, user);
            }
        } catch (DaoException e) {
            e.printStackTrace();
            return new ServiceResult(Status.ERROR, DATABASE_ERROR.message, id);
        } catch (IOException e) {
            e.printStackTrace();
            return new ServiceResult(Status.ERROR, UPLOAD_FAILED.message, id);
        }
        return new ServiceResult(Status.SUCCESS, UPDATE_BACKGROUND_SUCCESS.message, fileName);
    }

    /**
     * 负责将文件写入文件，并返回文件名
     *
     * @param part 文件
     * @name uploadFile
     * @notice none
     */
    @Override
    public ServiceResult uploadFile(Part part) {
        String fileName;
        try {
            fileName = toFileName(part);
        } catch (IOException e) {
            e.printStackTrace();
            return new ServiceResult(Status.ERROR, UPLOAD_FAILED.message, part);
        }
        return new ServiceResult(Status.SUCCESS, UPLOAD_SUCCESS.message, fileName);
    }
}
