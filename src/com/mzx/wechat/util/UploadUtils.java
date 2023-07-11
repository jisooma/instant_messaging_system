package com.mzx.wechat.util;

import com.mzx.wechat.exception.ServiceException;
import com.mzx.wechat.model.po.Chat;
import com.mzx.wechat.model.po.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;

import static com.mzx.wechat.util.UUIDUtils.getUUID;

/**
 * @program wechat
 * @description 用于上传文件
 */
public class UploadUtils {


    /**
     * 用于上传一个文件，返回该文件的文件名(写入photo文件夹)
     *
     * @return String 存储的文件名
     * @name toPhotoName
     * @notice none
     */
    public static String toPhotoName(Part part) throws IOException {
        String head = part.getHeader("Content-Disposition");
        String filename = getUUID() + head.substring(head.lastIndexOf("."), head.lastIndexOf("\""));
        part.write("photo/"+filename);
        return filename;
    }
    /**
     * 用于上传一个文件，返回该文件的文件名(写入file文件夹)
     *
     * @return String 存储的文件名
     * @name toFileName
     * @notice none
     */
    public static String toFileName(Part part) throws IOException {
        String head = part.getHeader("Content-Disposition");
        String filename = getUUID() + head.substring(head.lastIndexOf("."), head.lastIndexOf("\""));
        part.write("file/"+filename);
        return filename;
    }

}
