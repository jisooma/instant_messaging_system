
package com.mzx.wechat.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mzx.wechat.dao.*;
import com.mzx.wechat.factory.DaoProxyFactory;
import com.mzx.wechat.factory.ServiceProxyFactory;
import com.mzx.wechat.model.builder.MessageVOBuilder;
import com.mzx.wechat.model.po.Chat;
import com.mzx.wechat.model.po.Member;
import com.mzx.wechat.model.po.Message;
import com.mzx.wechat.model.po.User;
import com.mzx.wechat.model.vo.MessageVO;
import com.mzx.wechat.service.MessageService;
import com.mzx.wechat.service.impl.MessageServiceImpl;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.*;

import static com.mzx.wechat.service.constants.MessageType.FILE;
import static com.mzx.wechat.service.constants.MessageType.IMG;
import static com.mzx.wechat.util.StringUtils.toLegalText;
import static com.mzx.wechat.util.StringUtils.toLegalTextIgnoreTag;

/**
 * @description 用于提供实时聊天服务
 */

// 使用 javax.websocket.*的 API，可以将一个普通 Java 对象（POJO）使用 @ServerEndpoint 注释作为 WebSocket 服务器的端点
@ServerEndpoint("/server/chat/{user}")
public class ChatServer {

    /*
     **************************************************************
     *              静态类变量，负责记录系统数据
     **************************************************************
     */

    private static final MessageService MESSAGE_SERVICE = (MessageService) new ServiceProxyFactory().getProxyInstance(new MessageServiceImpl());
    private static final UserDao USER_DAO = (UserDao) DaoProxyFactory.getInstance().getProxyInstance(UserDao.class);
    private static final ChatDao CHAT_DAO = (ChatDao) DaoProxyFactory.getInstance().getProxyInstance(ChatDao.class);
    private static final MessageDao MESSAGE_DAO = (MessageDao) DaoProxyFactory.getInstance().getProxyInstance(MessageDao.class);
    private static final MemberDao MEMBER_DAO = (MemberDao) DaoProxyFactory.getInstance().getProxyInstance(MemberDao.class);
    private static final RecordDao RECORD_DAO = (RecordDao) DaoProxyFactory.getInstance().getProxyInstance(RecordDao.class);
    /**
     * 用于装载系统中在线用户的server
     */
    // ConcurrentHashMap 一个并发容器
    private static final ConcurrentHashMap<String, ChatServer> SERVER_MAP = new ConcurrentHashMap<>();
    /**
     * 启动一个消息队列缓存
     */
    //ScheduledThreadPoolExecutor可以用来在给定延时后执行异步任务或者周期性执行任务
    private static final ScheduledExecutorService SERVICE = new ScheduledThreadPoolExecutor(10);
    /**
     * 用于记录当前系统在线用户人数
     */
    private static int onlineCount = 0;
    private final String ALREADY_ONLINE = "您已经在一个新的浏览器上登陆，系统将自动断开与本页面的连接，页面将不可用";
    /**
     * 将消息存入数据库的时间间隔(秒)
     */
    private final Long SAVE_PERIOD_SECOND = 1L;
    /**
     * 启动消息队列定时器的延迟时间
     */
    private final Long INIT_DELAY = 0L;


    /*
     **************************************************************
     *              负责记录连接用户的数据
     **************************************************************
     */

    private Session session;
    private User user;

    /**
     * 这个集合装载所有与该用户具有聊天关系的用户成员集合，onOpen方法执行时装载
     */
    private Map<BigInteger, Member> memberMap = new HashMap<>();
    /**
     * 负责定时将用户的消息队列存入数据库
     */
    //它的数据格式与json类似，但是在存储时对数字、多字节字符、数组等都做了很多优化，减少了无用的字符，二进制格式，也保证不用字符化带来额外的存储空间的增加。
    private MessageTask messageTask = new MessageTask();

    @OnOpen
    public void onOpen(Session session, @PathParam("user") String userId) throws IOException {
        //检查是否已经建立过连接
        ChatServer server = SERVER_MAP.get(userId);
        if (server != null && server.session != null && server.session.isOpen()) {
            server.session.getBasicRemote().sendText(ALREADY_ONLINE);
            server.session.close();
        }
        server = new ChatServer();
        //装载用户数据，作为缓存
        server.session = session;
        server.user = USER_DAO.getUserById(userId);
        if (server.user == null) {
            try {
                server.session.getBasicRemote().sendText("当前用户不存在");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //启动消息队列的定时器，定时将消息存入数据库
        SERVICE.scheduleAtFixedRate(server.messageTask, INIT_DELAY, SAVE_PERIOD_SECOND, TimeUnit.SECONDS);
        //加载用户所在的所有聊天中的所有成员
        List<Chat> chatList = CHAT_DAO.listByUserId(userId);
        for (Chat chat : chatList) {
            List<Member> list = MEMBER_DAO.listMemberByChatId(chat.getId());
            for (Member member : list) {
                if (!String.valueOf(member.getUserId()).equals(userId)) {
                    //将除了自己的所有成员加入容器
                    server.memberMap.put(member.getId(), member);
                }
            }
        }
        //将用户id对应的对象装载到Map容器
        SERVER_MAP.put(userId, server);
        onlineCount++;
        System.out.println("有新连接加入！当前在线人数为" + onlineCount);
        System.out.println("当前系统用户：" + userId);
    }

    @OnMessage
    //@PathParam("user")是获取url模板上数据的（/{id}）
    public void onMessage(String msg, Session session, @PathParam("user") String userId) throws IOException {
        System.out.println("来自客户端的消息:" + msg + " 客户端请求参数 :" + userId + " 客户端session :" + session.toString());
        //从容器中获取server
        ChatServer server = SERVER_MAP.get(userId);
        //客户段上传到服务器是Message,服务器发送到客户端是MessageVo
        Message message = JSON.toJavaObject(JSON.parseObject(msg), Message.class);
        //先过滤消息内容,如果是文件和图片则不过滤标签
        if (FILE.toString().equalsIgnoreCase(message.getType()) || IMG.toString().equalsIgnoreCase(message.getType())) {
            message.setContent(toLegalTextIgnoreTag(message.getContent()));
        } else {
            message.setContent(toLegalText(message.getContent()));
        }
        if (message.getContent().trim().isEmpty()) {
            return;
        }
        //向消息的接收者和自己发送消息
        sendMessage(message, userId);
        //将po对象加入消息存储队列
        server.messageTask.enQueue(message);
    }


    @OnClose
    public void onClose(@PathParam("user") String userId) {
        onlineCount--;
        System.out.println(SERVER_MAP.get(userId));
        System.out.println("有一连接关闭！当前在线人数为" + onlineCount);
    }

    @OnError
    public void onError(Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }


    /**
     * 用于向系统中的在线用户发送一条通知
     *
     * @param message 消息
     * @param userId  消息接收者的id
     * @return
     * @name sendNotify
     * @notice none
     */
    public static void sendNotify(Message message, BigInteger userId) {
        //从容器中获取server
        ChatServer server = SERVER_MAP.get(String.valueOf(userId));

        //发送给对应用户
        if (server != null && server.session != null && server.session.isOpen()) {
            //创建vo对象(使用建造者模式的链式调用)
            User sender = USER_DAO.getUserById(message.getSenderId());

            MessageVO messageVo = new MessageVOBuilder().setSenderId(sender.getId())
                    .setSenderName(sender.getName()).setChatId(message.getChatId())
                    .setContent(message.getContent()).setSenderPhoto(sender.getPhoto())
                    .setTime(message.getTime()).setType(message.getType()).build();
            String jsonString = JSONObject.toJSONString(messageVo);
            try {
                //向前台web页面推送消息，便用websocket来做消息推送，websocket  session发送文本消息有两个方法：getAsyncRemote()和getBasicRemote()
                server.session.getBasicRemote().sendText(jsonString);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 用于向系统中的在线用户的聊天管理map中添加新成员
     *
     * @param newMember  成员
     * @param message 加群打招呼信息
     * @name addMember
     * @notice none
     */
    public static void addMember(Member newMember, Message message) {
        //根据群聊新成员的群聊id拿到此群聊所有的成员信息
        List<Member> memberList = MEMBER_DAO.listMemberByChatId(newMember.getChatId());
        for (Member oldMember : memberList) {
            //除开自己
            if(newMember.getUserId().equals(oldMember.getUserId())){
                continue;
            }
            //从容器中获取该新成员的server
            ChatServer server = SERVER_MAP.get(String.valueOf(newMember.getUserId()));
            //把一个旧成员加入这个新成员的memberMap
            if (server != null && server.session != null && server.session.isOpen()) {
                server.memberMap.put(oldMember.getId(), oldMember);
            }
            //把新成员自己加入到旧成员对方的memberMap
            server = SERVER_MAP.get(String.valueOf(oldMember.getUserId()));
            if (server != null && server.session != null && server.session.isOpen()) {
                server.memberMap.put(newMember.getId(), newMember);
            }
        }
        //使用该成员的server在这个聊天中向该群成员发送打招呼消息
        ChatServer server = SERVER_MAP.get(String.valueOf(newMember.getUserId()));
        if (server != null && server.session != null && server.session.isOpen()) {
            try {
                server.sendMessage(message, String.valueOf(newMember.getUserId()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 将用户的消息定时插入到数据库，同时插入生成的消息记录
     * @notice none
     */
    private class MessageTask extends TimerTask {
        /**
         * 消息队列，用户发送的消息先缓存在这里，连接关闭时插入数据库
         */
        private Queue<Message> messageQueue = new LinkedBlockingQueue<>();

        /**
         * 将一条消息加入到队列中
         *
         * @param message 用户发送的消息
         * @name enQueue
         */
        private void enQueue(Message message) {
            this.messageQueue.add(message);
        }

        @Override
        public void run() {
            for (Message message : this.messageQueue) {
                //将消息插入数据库，并从队列移除
                MESSAGE_SERVICE.insertMessage(message);
                this.messageQueue.remove();
            }
        }
    }

    /**
     * 向与用户处于同一个聊天中的成员包括用户自己发送一条消息
     *
     * @param message 消息
     * @name sendMessage
     * @notice none
     */
    synchronized private void sendMessage(Message message, String userId) throws IOException {
        //从容器中获取server
        ChatServer server = SERVER_MAP.get(userId);
        //创建vo对象(使用建造者模式的链式调用)
        MessageVO messageVo = new MessageVOBuilder().setSenderId(server.user.getId())
                .setSenderName(server.user.getName()).setChatId(message.getChatId())
                .setContent(message.getContent()).setSenderPhoto(server.user.getPhoto())
                .setTime(message.getTime()).setType(message.getType()).build();
        String jsonString = JSONObject.toJSONString(messageVo);
        //发送给自己
        if (server.session.isOpen()) {
            server.session.getBasicRemote().sendText(jsonString);
        }
        //遍历与用户具有聊天关系的集合，将消息发给指定用户，如果在线则发送消息
        //一个Map对象，使用keySet()方法获取所有的key值
        Set<BigInteger> keys = server.memberMap.keySet();
        for (BigInteger key : keys) {
            Member member = server.memberMap.get(key);
            //获取接收者server
            ChatServer receiver = SERVER_MAP.get(String.valueOf(member.getUserId()));
            //当该成员处于消息对应的聊天中,并且在线时时给他发送消息
            if (member.getChatId().equals(message.getChatId()) && receiver != null && receiver.session != null && receiver.session.isOpen()) {
                try {
                    receiver.session.getBasicRemote().sendText(jsonString);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
