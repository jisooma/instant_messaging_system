

package com.mzx.wechat.controller.impl.listener;

import com.mzx.wechat.provider.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**

 * @program wechat
 * @description 负责监听servlet的初始化和销毁事件

 */

@WebListener
public class ServletContextListener implements javax.servlet.ServletContextListener {

    private static final ConcurrentHashMap<String, Provider> providerMap = new ConcurrentHashMap<>();

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        /**
         * 用来装载provider的容器
         */
        //注册服务
        providerMap.put("userProvider", new UserProvider());
        providerMap.put("chatProvider", new ChatProvider());
        providerMap.put("uploadProvider", new UploadProvider());
        providerMap.put("friendProvider", new FriendProvider());
        providerMap.put("messageProvider",new MessageProvider());
        //将controllerMap注入ServletContext
        ServletContext sc = sce.getServletContext();
        sc.setAttribute("providerMap", providerMap);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
