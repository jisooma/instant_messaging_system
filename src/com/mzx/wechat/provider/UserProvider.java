

package com.mzx.wechat.provider;

import com.mzx.wechat.controller.constant.RequestMethod;
import com.mzx.wechat.controller.constant.WebPage;
import com.mzx.wechat.factory.ServiceProxyFactory;
import com.mzx.wechat.model.dto.ServiceResult;
import com.mzx.wechat.model.po.Friend;
import com.mzx.wechat.model.po.Member;
import com.mzx.wechat.model.po.Message;
import com.mzx.wechat.model.po.User;
import com.mzx.wechat.provider.annotation.Action;
import com.mzx.wechat.provider.annotation.ActionProvider;
import com.mzx.wechat.service.ChatService;
import com.mzx.wechat.service.FriendService;
import com.mzx.wechat.service.UserService;
import com.mzx.wechat.service.constants.ServiceMessage;
import com.mzx.wechat.service.constants.Status;
import com.mzx.wechat.service.impl.ChatServiceImpl;
import com.mzx.wechat.service.impl.FriendServiceImpl;
import com.mzx.wechat.service.impl.UserServiceImpl;
import com.mzx.wechat.util.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigInteger;

import static com.mzx.wechat.util.BeanUtils.jsonToJavaObject;
import static com.mzx.wechat.util.ControllerUtils.returnJsonObject;

/**
 * @program im
 * @description 用于处理用户相关业务流程

 */
@ActionProvider(path = "/user")
public class UserProvider extends Provider {


    private final int AUTO_LOGIN_AGE = 60 * 60 * 24 *30;
    private final String AUTO_LOGIN_PATH = "/";
    private final UserService userService = (UserService) new ServiceProxyFactory().getProxyInstance(new UserServiceImpl());
    private final ChatService chatService = (ChatService) new ServiceProxyFactory().getProxyInstance(new ChatServiceImpl());
    private final FriendService friendService = (FriendService) new ServiceProxyFactory().getProxyInstance(new FriendServiceImpl());

    /**
     * 提供用户注册的业务流程
     *
     * @name register
     * @notice none
     */
    @Action(method = RequestMethod.REGISTER_DO)
    public void register(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        //request.getParameterMap()返回的是一个Map类型的值，
        // 该返回值记录着前端（如jsp页面）所提交请求中的请求参数和请求参数值的映射关系。
        // 这个返回值有个特别之处——只能读。不像普通的Map类型数据一样可以修
        User user = (User) BeanUtils.toObject(req.getParameterMap(), User.class);//得到一个user对象
        ServiceResult result;//定义一个服务返回结果，用于验证注册是否成功
        //检查用户注册信息9
        result = userService.checkRegister(user);
        if (Status.ERROR.equals(result.getStatus())) {
            req.setAttribute("message",result.getMessage());
            req.setAttribute("data",result.getData());
            req.getRequestDispatcher(WebPage.REGISTER_JSP.toString()).forward(req,resp);
            return;
        }
        //插入用户
        result = userService.insertUser(user);
        if (Status.ERROR.equals(result.getStatus())) {
            req.setAttribute("message",result.getMessage());
            req.setAttribute("data",result.getData());
            req.getRequestDispatcher(WebPage.REGISTER_JSP.toString()).forward(req,resp);
        } else {
            //注册成功后将用户添加到聊天总群中
            user = (User) result.getData();
            addToDefaultChat(user);
            //与系统账号加好友
            addToSystemChat(user);
            req.setAttribute("message",result.getMessage());
            req.setAttribute("data",result.getData());
            req.getRequestDispatcher(WebPage.LOGIN_JSP.toString()).forward(req,resp);
        }
    }


    /**
     * 提供用户登陆的业务流程
     *
     * @name login
     * @notice none
     */
    @Action(method = RequestMethod.LOGIN_DO)
    public void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) BeanUtils.toObject(req.getParameterMap(), User.class);
        ServiceResult result;
        if (user == null) {
            result = new ServiceResult(Status.ERROR, ServiceMessage.PARAMETER_NOT_ENOUGHT.message, null);
            req.setAttribute("message",result.getMessage());
            req.setAttribute("data",result.getData());
            req.getRequestDispatcher(WebPage.LOGIN_JSP.toString()).forward(req,resp);
            return;
        }
        //参数为false时，如存在会话，则返回该会话，否则返回NULL；
        HttpSession session = req.getSession(false);
        //检查用户是否已经建立会话并且已经具有登陆信息
        //.setAttribute 和 .getAttribute 都是基于HashMap的put方法和get方法实现的，一般叫键值对或者key-value，即通过键找到值。
        if (session == null || session.getAttribute("login") == null) {
                //如果是用户登陆，校验密码是否正确
                result = userService.checkPassword(user);
                if (Status.ERROR.equals(result.getStatus())) {
                    req.setAttribute("message",result.getMessage());
                    req.setAttribute("data",result.getData());
                    req.getRequestDispatcher(WebPage.LOGIN_JSP.toString()).forward(req,resp);
                    return;
                } else {
                    //校验密码成功时，给会话中添加用户信息
                    result = userService.getUser(user.getId());
                    user = (User) result.getData();
                    //                    //如果设置自动登陆，则添加cookie
                    if (req.getParameter("auto_login")!=null) {
                        setAutoLoginCookie(resp,req,  String.valueOf(user.getId()));
                    }

                }
        } else {
            //先从session获取用户信息，再更新用户信息到会话中
            user = (User) session.getAttribute("login");
            result = userService.getUser(user.getId());
        }
        req.getSession(true).setAttribute("login", result.getData());
        req.getRequestDispatcher(WebPage.INDEX_JSP.toString()).forward(req,resp);
    }

    /**
     * 提供获取用户个人信息的业务流程
     *
     * @name get
     * @notice none
     */
    @Action(method = RequestMethod.GET_DO)
    public void get(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) BeanUtils.toObject(req.getParameterMap(), User.class);
        ServiceResult result;
        //获取用户数据
        result = userService.getUser(user.getId());
        if (Status.ERROR.equals(result.getStatus())) {
            returnJsonObject(resp, result);
        } else {
            //获取数据成功时的处理
            resp.getWriter().write(result.getMessage());
        }
    }


    /**
     * 提供获取用户个人信息的业务流程
     *
     * @name logout
     * @notice none
     */
    @Action(method = RequestMethod.LOGOUT_DO)
    public void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session != null) {
            session.invalidate();
        }
        returnJsonObject(resp, new ServiceResult(Status.SUCCESS, ServiceMessage.LOGOUT_SUCCESS.message, null));
    }
    /**
     * 提供用户更新个人信息的业务流程
     *
     * @name update
     * @notice none
     */
    @Action(method = RequestMethod.UPDATE_DO)
    public void update(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = (User) jsonToJavaObject(req.getInputStream(), User.class);
        ServiceResult result;
        if (user != null && user.getWechatId() != null) {
            User oldUser = (User) userService.getUser(user.getId()).getData();
            if (!oldUser.getWechatId().equals(user.getWechatId())) {
                //如果请求要求修改微信名，先检查用户名是否合法
                result = userService.checkWechatId(user.getWechatId());
                if (Status.ERROR.equals(result.getStatus())) {
                    returnJsonObject(resp, result);
                    System.out.println("11111");
                    return;
                }
            }
        }
        //更新用户数据
        result = userService.updateUser(user);
        returnJsonObject(resp, result);
    }

    /**
     * 提供用户更新密码的业务流程
     *
     * @name updatePwd
     * @notice none
     */
    @Action(method = RequestMethod.UPDATEPASSWORD_DO)
    public void updatePwd(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String oldPwd = req.getParameter("old_password");
        String newPwd = req.getParameter("new_password");
        String userId = req.getParameter("user_id");
        ServiceResult result;
        //更新用户数据
        result = userService.updatePwd(oldPwd, newPwd, new BigInteger(userId));
        returnJsonObject(resp, result);
    }


    /**
     * 提供搜索用户的服务
     *
     * @name list
     * @notice none
     */
    @Action(method = RequestMethod.LIST_DO)
    public void list(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = (User) BeanUtils.toObject(req.getParameterMap(), User.class);
        ServiceResult result;
        result = userService.listUserLikeName(user.getName());
        if (Status.ERROR.equals(result.getStatus())) {
            returnJsonObject(resp, result);
            return;
        }
        returnJsonObject(resp, result);
    }

    /**
     * 提供自动登陆的服务
     *
     * @name list
     * @notice none
     */
    public void autoLogin(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("user_id".equalsIgnoreCase(cookie.getName())) {
                    ServiceResult result = userService.getUser(cookie.getValue());
                    if (Status.SUCCESS.equals(result.getStatus())) {
//                        addToDefaultChat((User) result.getData());
                        //如果获取用户信息成功则设置‘login’属性
                        HttpSession session = req.getSession();
                        session.setAttribute("login", result.getData());
                        return;
                    }
                }
            }
        }
    }


    /**
     * 设置用于自动登陆的cookie
     *
     * @param userId 用户id
     * @name setAutoLoginCookie
     * @notice none
     */
    private void setAutoLoginCookie(HttpServletResponse resp,HttpServletRequest req, String userId) {
        Cookie cookie = new Cookie("user_id", userId);
        cookie.setMaxAge(AUTO_LOGIN_AGE);
        cookie.setPath(req.getContextPath());
        resp.addCookie(cookie);
    }

    /**
     * 将一个用户添加到聊天总群(id=0)
     *
     * @param user 用户
     * @name addToDefaultChat
     * @notice none
     */
    private void addToDefaultChat(User user) {
        Member member = new Member();
        member.setChatId(BigInteger.valueOf(0));
        member.setUserId(user.getId());
        chatService.joinChat(new Member[]{member});
    }

    /**
     * 将一个用户添加到与系统的会话中
     *
     * @param user 用户
     * @name addToSystemChat
     * @notice none
     */
    private void addToSystemChat(User user) {
        Friend friend = new Friend();
        //系统添加用户账号为好友
        friend.setUserId(UserServiceImpl.systemId);
        friend.setFriendId(user.getId());
        friendService.addFriend(friend);
        //用户添加系统账号为好友
        friend.setAlias(null);
        friend.setUserId(user.getId());
        friend.setFriendId(UserServiceImpl.systemId);
        friendService.addFriend(friend);
        //将用户和系统账号（id=0）添加到同一个聊天中
        chatService.createFriendChat(friend);
        //插入一条消息
        Message message = new Message();
        message.setContent("欢迎使用IM在线聊天系统，我们是本系统的开发者。" +
                "开发者信息\n" +
                "开发者：第一组带飞\n" +
                "联系邮箱：\n" );
    }
}
