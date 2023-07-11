

package com.mzx.wechat.provider;

import com.mzx.wechat.controller.constant.RequestMethod;
import com.mzx.wechat.controller.constant.WebPage;
import com.mzx.wechat.exception.ServiceException;
import com.mzx.wechat.provider.annotation.Action;
import com.mzx.wechat.provider.annotation.ActionProvider;
import com.mzx.wechat.util.ControllerUtils;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

import static com.mzx.wechat.controller.constant.ControllerMessage.REQUEST_INVALID;
import static com.mzx.wechat.controller.constant.ControllerMessage.SYSTEM_EXECEPTION;

/**
 * @description 所有ActionProvider的父类，提供共有的doAction方法
 */
public class Provider {


    /**
     * 负责将请求分发到对应的Action方法
     *
     * @param req  请求
     * @param resp 响应
     * @name doAction
     * @notice none
     */
    public void doAction(HttpServletRequest req, HttpServletResponse resp) {
        //获取请求中的method参数，转换成对应的枚举类型
        try {
            RequestMethod requestMethod = ControllerUtils.valueOf(req.getParameter("method"));
            if (RequestMethod.INVALID_REQUEST.equals(requestMethod)) {
                toErrorPage("无效的访问链接，系统无法识别您的请求指向的服务内容：" + req.getRequestURI(), req, resp);
                return;
            } else {
                boolean isMacth =false;
                //根据方法上的注解找到对应的Action方法并执行
                //获得此类实现的所有公有方法
                Method[] methods = this.getClass().getMethods();
                for (Method m : methods) {
                    //返回该元素的指定类型的注释
                    Action action = m.getAnnotation(Action.class);
                    if (action != null && action.method().equals(requestMethod)) {
                        try {
                            m.invoke(this, req, resp);
                            isMacth=true;
                        } catch (ServiceException e) {
                            //服务层抛出的异常信息与用户的操作有关，不包含底层细节，可以向用户输出
                            e.printStackTrace();
                            toErrorPage(e.getMessage(), req, resp);
                            return;
                        }
                    }
                }
                if(!isMacth){
                    Logger logger = Logger.getLogger(Provider.class);
                    logger.error("请求的方法没有对应的action方法");
                    toErrorPage("无效的访问链接，系统无法识别您的请求指向的服务内容"+ req.getRequestURI(), req, resp);
                }
            }
        } catch (ExceptionInInitializerError e) {
            e.printStackTrace();
            Logger logger = Logger.getLogger(Provider.class);
            logger.error(e.getStackTrace());
            //此处的异常包含细节信息，对客户端隐藏
            toErrorPage(SYSTEM_EXECEPTION.message, req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            Logger logger = Logger.getLogger(Provider.class);
            logger.debug(e.getStackTrace());
            //此处的异常包含细节信息，对客户端隐藏
            toErrorPage(REQUEST_INVALID.message, req, resp);
        }
    }

    /**
     * 转发到错误界面，向客户端输出错误信息
     *
     * @param message 异常描述信息
     * @name toErrorPage
     * @notice none
     */
    public static void toErrorPage(String message, HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute("message", message);
        try {
            req.getRequestDispatcher(WebPage.ERROR_JSP.toString()).forward(req, resp);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
            try {
                resp.getWriter().write("服务器异常");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * 返回这个Provider的url映射路径
     * @name getPath
     * @notice none
     */
    public String getPath() {
        return this.getClass().getAnnotation(ActionProvider.class).path();
    }
}
