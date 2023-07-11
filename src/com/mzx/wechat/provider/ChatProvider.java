
package com.mzx.wechat.provider;

import com.mzx.wechat.controller.constant.RequestMethod;
import com.mzx.wechat.factory.ServiceProxyFactory;
import com.mzx.wechat.model.dto.ServiceResult;
import com.mzx.wechat.model.po.Chat;
import com.mzx.wechat.model.po.Member;
import com.mzx.wechat.model.po.Message;
import com.mzx.wechat.model.po.User;
import com.mzx.wechat.provider.annotation.Action;
import com.mzx.wechat.provider.annotation.ActionProvider;
import com.mzx.wechat.server.ChatServer;
import com.mzx.wechat.service.ChatService;
import com.mzx.wechat.service.MessageService;
import com.mzx.wechat.service.constants.ServiceMessage;
import com.mzx.wechat.service.constants.Status;
import com.mzx.wechat.service.impl.ChatServiceImpl;
import com.mzx.wechat.service.impl.MessageServiceImpl;
import com.mzx.wechat.util.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigInteger;

import static com.mzx.wechat.util.BeanUtils.jsonToJavaObject;
import static com.mzx.wechat.util.ControllerUtils.returnJsonObject;

/**
 * @description 负责控制聊天服务流程

 */
@ActionProvider(path = "/chat")
public class ChatProvider extends Provider {

    private final ChatService chatService = (ChatService) new ServiceProxyFactory().getProxyInstance(new ChatServiceImpl());
    private final MessageService messageService = (MessageService) new ServiceProxyFactory().getProxyInstance(new MessageServiceImpl());

    /**
     * 提供用户创建群聊的服务流程，不提供创建个人私聊
     *
     * @name createChat
     * @notice none
     */
    @Action(method = RequestMethod.ADD_DO)
    public void createGroupChat(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       //将输入流中的json数据转化成java对象
        Chat chat = (Chat) jsonToJavaObject(req.getInputStream(), Chat.class);
        ServiceResult result;
        //创建一个群聊
        result = chatService.createChat(chat, true);
        if (Status.ERROR.equals(result.getStatus())) {
            returnJsonObject(resp, result);
            return;
        }
        //从chatService中重新获取chat的信息
        chat = (Chat) result.getData();
        //将用户加入群聊
        Member member = new Member();
        member.setUserId(chat.getOwnerId());
        member.setChatId(chat.getId());
        result = chatService.joinChat(new Member[]{member});
        if (Status.ERROR.equals(result.getStatus())) {
            returnJsonObject(resp, result);
            return;
        }
        //如果两个操作都成功，返回创建群聊成功和群号，覆盖下层service的消息
        result.setMessage(ServiceMessage.CREATE_GROUP_CHAT_SUCCESS.message + chat.getNumber() + ServiceMessage.PLEASE_JOIN_CHAT.message);
        returnJsonObject(resp, result);
    }

    /**
     * 提供用户加入群聊的服务
     *
     * @name joinChat
     * @notice none
     */
    @Action(method = RequestMethod.JOIN_DO)
    public void joinGroupChat(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String number = req.getParameter("number");
        String userId = req.getParameter("user_id");
        String apply = req.getParameter("apply");
        ServiceResult result;
        result = chatService.joinChatByNumber(new BigInteger(userId), number, apply);
        if (Status.ERROR.equals(result.getStatus())) {
            returnJsonObject(resp, result);
            return;
        }
        //加群成功后通知群里所有在线用户
        Member member = (Member) result.getData();
        //生成打招呼消息
        Message message = chatService.getHelloMessage(member);
        messageService.insertMessage(message);
        ChatServer.addMember(member, message);
        returnJsonObject(resp, result);
    }

    /**
     * 提供退出群聊的服务
     *
     * @name quitChat
     * @notice none
     */
    @Action(method = RequestMethod.QUIT_DO)
    public void quitChat(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Member member = (Member) jsonToJavaObject(req.getInputStream(), Member.class);
        ServiceResult result;
        result = chatService.quitChat(member);
        returnJsonObject(resp, result);
    }

    /**
     * 提供将一个聊天成员移除的服务
     *
     * @name remove
     * @notice none
     */
    @Action(method = RequestMethod.REMOVE_DO)
    public void remove(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String memberId = req.getParameter("member_id");
        ServiceResult result;
        //检查操作用户
        User user = (User) req.getSession().getAttribute("login");
        if (chatService.isOwner(new BigInteger(memberId), user.getId())) {
            result = chatService.removeFromChat(new BigInteger(memberId));
        }else{
            result = new ServiceResult(Status.ERROR,ServiceMessage.NOT_OWNER.message,null);
        }
        returnJsonObject(resp, result);
    }


    /**
     * 提供获取一个用户的聊天列表的服务
     *
     * @name listChat
     * @notice none
     */
    @Action(method = RequestMethod.LIST_DO)
    public void listChat(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) BeanUtils.toObject(req.getParameterMap(), User.class);
        ServiceResult result;
        result = chatService.listChat(user);
        //用户将服务结果转换成json数据并返回客户端
        returnJsonObject(resp, result);
    }

    /**
     * 提供获取一个用户的一个聊天的服务
     *
     * @name getChat
     * @notice none
     */
    @Action(method = RequestMethod.GET_DO)
    public void getChat(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String number = req.getParameter("number");
        String userId = req.getParameter("user_id");
        ServiceResult result;
        result = chatService.getChat(number, new BigInteger(userId));
        returnJsonObject(resp, result);
    }

    /**
     * 提供查看群成员的服务
     *
     * @name listMember
     * @notice none
     */
    @Action(method = RequestMethod.MEMBER_DO)
    public void listMember(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String chatId = req.getParameter("chat_id");
        ServiceResult result;
        result = chatService.listMember(new BigInteger(chatId));
        returnJsonObject(resp, result);
    }


}
