
package com.mzx.wechat.provider;

import com.mzx.wechat.controller.constant.RequestMethod;
import com.mzx.wechat.factory.ServiceProxyFactory;
import com.mzx.wechat.model.dto.ServiceResult;
import com.mzx.wechat.provider.annotation.Action;
import com.mzx.wechat.provider.annotation.ActionProvider;
import com.mzx.wechat.service.UploadService;
import com.mzx.wechat.service.impl.UploadServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

import static com.mzx.wechat.util.ControllerUtils.returnJsonObject;

/**
 * @description 负责上传文件流程

 */
@ActionProvider(path = "/upload")
public class UploadProvider extends Provider {
    private final UploadService uploadService = (UploadService) new ServiceProxyFactory().getProxyInstance(new UploadServiceImpl());

    //@Action(method = RequestMethod.UPLOADPHOTO_DO)
//    public void uploadPhoto(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
//        Part part = req.getPart("photo");
//        Object id = req.getParameter("id");
//        String tableName = req.getParameter("table");
//        ServiceResult result;
//        result = uploadService.uploadPhoto(part, id, tableName);
//        returnJsonObject(resp, result);
//    }

    @Action(method = RequestMethod.BACKGROUND_DO)
    public void uploadBackground(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Part part = req.getPart("photo");
        Object id = req.getParameter("id");
        ServiceResult result;
        result = uploadService.uploadBackground(part, id);
        returnJsonObject(resp, result);
    }


    @Action(method = RequestMethod.UPLOADFILE_DO)
    public void uploadfile(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Part part = req.getPart("file");
        ServiceResult result;
        result = uploadService.uploadFile(part);
        returnJsonObject(resp, result);
    }
}
