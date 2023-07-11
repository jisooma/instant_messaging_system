
package com.mzx.wechat.provider;

import com.mzx.wechat.controller.constant.RequestMethod;
import com.mzx.wechat.factory.ServiceProxyFactory;
import com.mzx.wechat.model.dto.ServiceResult;
import com.mzx.wechat.provider.annotation.Action;
import com.mzx.wechat.provider.annotation.ActionProvider;
import com.mzx.wechat.service.MessageService;
import com.mzx.wechat.service.constants.ServiceMessage;
import com.mzx.wechat.service.impl.MessageServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigInteger;

import static com.mzx.wechat.util.ControllerUtils.returnJsonObject;

/**
 * @description 提供消息记录的服务流程控制

 */
@ActionProvider(path = "/message")
public class MessageProvider extends Provider {
    private final MessageService messageService = (MessageService) new ServiceProxyFactory().getProxyInstance(new MessageServiceImpl());


    /**
     * 提供获取一个聊天的所有聊天记录的服务
     *
     * @name listMessage
     * @notice none
     */
    @Action(method = RequestMethod.LIST_DO)
    public void listMessage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("user_id");
        String chatId = req.getParameter("chat_id");
        String page = req.getParameter("page");
        ServiceResult result = null;
        result = messageService.listAllMessage(new BigInteger(userId), new BigInteger(chatId), new Integer(page));
        returnJsonObject(resp, result);
    }


    /**
     * 提供获取所有未读消息的服务
     *
     * @name listUnreadMessage
     * @notice none
     */
    @Action(method = RequestMethod.UNREAD_DO)
    public void listUnreadMessage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("user_id");
        String page = req.getParameter("page");
        String chatId = req.getParameter("chat_id");
        ServiceResult result;
        //如果没有写chatId，则加载所有未读消息
        if (chatId == null) {
            result = messageService.listAllUnreadMessage(new BigInteger(userId), new Integer(page));
        } else {
            result = messageService.listUnreadMessage(new BigInteger(userId), new BigInteger(chatId), new Integer(page));
        }
        returnJsonObject(resp, result);
    }


    /**
     * 提供将一个聊天中的消息设置为已读的服务
     *
     * @name read
     * @notice none
     */
    @Action(method = RequestMethod.READ_DO)
    public void read(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("user_id");
        String chatId = req.getParameter("chat_id");
        ServiceResult result = new ServiceResult();
        result.setMessage(ServiceMessage.ALREADY_READ.message);
        messageService.setAlreadyRead(new BigInteger(userId), new BigInteger(chatId));
        returnJsonObject(resp, result);
    }

    /**
     * 提供将一个聊天中的消息记录清除服务
     *
     * @name clear
     * @notice none
     */
    @Action(method = RequestMethod.CLEAR_DO)
    public void clear(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("user_id");
        String chatId = req.getParameter("chat_id");
        ServiceResult result;
        result = messageService.removeAllMessage(new BigInteger(userId), new BigInteger(chatId));
        returnJsonObject(resp, result);
    }

    /**
     * 提供将一个聊天中的消息记录导出的服务
     *
     * @name export
     * @notice none
     */
    @Action(method = RequestMethod.EXPORT_DO)
    public void export(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("user_id");
        String chatId = req.getParameter("chat_id");
        ServiceResult result;
        result = messageService.exportMessage(new BigInteger(userId), new BigInteger(chatId));
        returnJsonObject(resp, result);
    }


}
