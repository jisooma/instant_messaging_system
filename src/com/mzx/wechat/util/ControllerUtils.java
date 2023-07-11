

package com.mzx.wechat.util;

import com.alibaba.fastjson.JSON;
import com.mzx.wechat.controller.constant.RequestMethod;
import com.mzx.wechat.model.dto.ServiceResult;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program wechat
 * @description 控制层工具类
 */
public class ControllerUtils {


    /**
     * 返回请求中method对应的RequestMethod枚举项
     *
     * @param name 请求中的method参数值
     * @name valueOf
     * @notice none
     * @see RequestMethod
     */
    public static RequestMethod valueOf(String name) {
        try {
            name = name.toUpperCase().replaceAll("\\.", "_");
            return RequestMethod.valueOf(name);
        } catch (IllegalArgumentException | NullPointerException e) {
            //此处异常表明请求参数没有匹配到任何服务,为无效请求
            e.printStackTrace();
            return RequestMethod.INVALID_REQUEST;
        }
    }

    /**
     * 用户将服务结果转换成json数据并返回客户端
     *
     * @param resp   响应
     * @param result 服务结果
     * @return
     * @name returnJsonObject
     * @notice none
     */
    public static void returnJsonObject(HttpServletResponse resp, ServiceResult result) throws IOException {
        JSON json = (JSON) JSON.toJSON(result);
        resp.getWriter().write(json.toJSONString());
        Logger logger = Logger.getLogger(ControllerUtils.class);
        logger.info(json.toJSONString());
    }
}

