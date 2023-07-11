

package com.mzx.wechat.controller.impl.servlet;

import com.mzx.wechat.controller.annotation.ControllerConfig;
import com.mzx.wechat.controller.constant.ControllerMessage;
import com.mzx.wechat.provider.Provider;
import com.mzx.wechat.util.ControllerUtils;
import org.apache.log4j.Logger;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import static com.mzx.wechat.provider.Provider.toErrorPage;

/**

 * @program wechat
 * @description 接收客户端请求，将其转发到controller

 */
//@MultipartConfig(location = "tomcat/webapps/wechat/upload")
@MultipartConfig(location = "E:\\tomcat\\apache-tomcat-9.0.40\\webapps\\wechat-dev\\web\\upload")
//@MultipartConfig(location = "C:\\Users\\Misterchaos\\Documents\\Java Develop Workplaces\\IDEA workspace\\wechat\\out\\artifacts\\wechat_war_exploded\\upload")
@WebServlet("/wechat/*")
public class MyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        this.doPost(req, resp);
    }
    /**
     * 负责将请求转发到url对应的Provider
     *
     * @param req  请求
     * @param resp 响应
     * @name doPost
     * @notice none
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, Provider>providerMap = (Map<String, Provider>) getServletContext().getAttribute("providerMap");
        //根据请求拿到url
        String url = req.getRequestURI();
        //拿到所有的key值
        Set<String> keys = providerMap.keySet();
        //追踪产生此日志的类 MyServlet.class
        Logger logger = Logger.getLogger(MyServlet.class);
        //
        logger.info("[请求url:]"+url+"[匹配provider]:"+url.substring(14));
        boolean isMatch=false;
        for (String key : keys) {
            //解析注解中的path信息，匹配ActionProvider
            //根据key值拿到path
            String path =providerMap.get(key).getPath();
            if (url.substring(14).equalsIgnoreCase(path)) {
                providerMap.get(key).doAction(req, resp);
                logger.info("provider 分发完毕");
                isMatch=true;
            }
        }
        if(!isMatch){
            toErrorPage(ControllerMessage.REQUEST_INVALID.message,req,resp);
            logger.info("该请求没有匹配provider :"+url.substring(14));
            return;
        }
//        logger.info("响应结果 "+resp.getOutputStream());
    }
}


