
package com.mzx.wechat.service.impl;

import com.mzx.wechat.dao.*;
import com.mzx.wechat.exception.DaoException;
import com.mzx.wechat.exception.ServiceException;
import com.mzx.wechat.factory.DaoProxyFactory;
import com.mzx.wechat.model.builder.MessageVOBuilder;
import com.mzx.wechat.model.dto.ServiceResult;
import com.mzx.wechat.model.po.Member;
import com.mzx.wechat.model.po.Message;
import com.mzx.wechat.model.po.Record;
import com.mzx.wechat.model.po.User;
import com.mzx.wechat.model.vo.MessageVO;
import com.mzx.wechat.service.MessageService;
import com.mzx.wechat.service.constants.ServiceMessage;
import com.mzx.wechat.service.constants.Status;

import java.io.File;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

import static com.mzx.wechat.service.constants.ServiceMessage.*;
import static com.mzx.wechat.util.UUIDUtils.getUUID;

/**

 * @description 负责提供消息和聊天记录的服务
 */
public class MessageServiceImpl implements MessageService {


    private final ChatDao chatDao = (ChatDao) DaoProxyFactory.getInstance().getProxyInstance(ChatDao.class);
    private final MessageDao messageDao = (MessageDao) DaoProxyFactory.getInstance().getProxyInstance(MessageDao.class);
    private final RecordDao recordDao = (RecordDao) DaoProxyFactory.getInstance().getProxyInstance(RecordDao.class);
    private final MemberDao memberDao = (MemberDao) DaoProxyFactory.getInstance().getProxyInstance(MemberDao.class);
    private final UserDao userDao = (UserDao) DaoProxyFactory.getInstance().getProxyInstance(UserDao.class);

    /**
     * 将一条消息存入数据库，同时给聊天中的所有成员生成一份聊天记录
     *
     * @param message 要插入的消息
     * @name insertMessage
     * @notice none
     */
    @Override
    public void insertMessage(Message message) {
        try {
            //消息加密

            //将消息插入数据库
            if (messageDao.insert(message) != 1) {
                throw new ServiceException(DATABASE_ERROR.message + message.toString());
            }
            //检查是否有时间参数
            if(message.getTime()==null){
                throw new ServiceException(MISSING_TIME.message + message.toString());
            }
            if(message.getType()==null){
                throw new ServiceException(MISSING_TYPE.message + message.toString());
            }
            message = messageDao.getMessageBySenderIdAndChatIdAndTime(message.getSenderId(), message.getChatId(), message.getTime());
            //对每条消息给聊天中所有成员产生消息记录
            //加载用户所在的所有聊天中的所有成员,给所有成员插入记录
            List<Member> memberList = memberDao.listMemberByChatId(message.getChatId());
            if (memberList != null && memberList.size() > 0) {
                for (Member member : memberList) {
                    Record record = new Record();
                    record.setMessageId(message.getId());
                    record.setUserId(member.getUserId());
                    if (recordDao.insert(record) != 1) {
                        throw new ServiceException(DATABASE_ERROR.message + record.toString());
                    }
                }
            }
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }

    /**
     * 获取一个用户在一个聊天中的所有消息记录，不包括被删除的消息记录
     *
     * @param userId 用户id
     * @param chatId 聊天id
     * @param page   页数
     * @return
     */
    @Override
    public ServiceResult listAllMessage(Object userId, Object chatId, int page) {
        int limit = 200;
        int offset = (page - 1) * limit;
        //检查页数
        if (offset < 0) {
            return new ServiceResult(Status.ERROR, ServiceMessage.PAGE_INVALID.message, null);
        }
        List<MessageVO> messageVOList = new LinkedList<>();

        //数据判空
        if (userId == null || chatId == null) {
            return new ServiceResult(Status.ERROR, ServiceMessage.PARAMETER_NOT_ENOUGHT.message, null);
        }
        try {
            User user = userDao.getUserById(userId);
            //检查用户是否存在
            if (user == null) {
                return new ServiceResult(Status.ERROR, ServiceMessage.ACCOUNT_NOT_FOUND.message, null);
            }
            //查询数据库
            List<Message> messageList = messageDao.listMessageByUserIdAndChatIdDesc(userId, chatId, limit, offset);
            //检查数据是否存在
            if (messageList == null || messageList.size() == 0) {
                return new ServiceResult(Status.SUCCESS, null, messageList);
            }
            toMessageVOObject(messageVOList, messageList);
        } catch (DaoException e) {
            e.printStackTrace();
            return new ServiceResult(Status.ERROR, DATABASE_ERROR.message, chatId);

        }
        return new ServiceResult(Status.SUCCESS, null, messageVOList);
    }

    /**
     * 导出一个用户在一个聊天中的所有消息记录，返回文件名
     *
     * @param userId 用户id
     * @param chatId 聊天id
     */
    @Override
    public ServiceResult exportMessage(Object userId, Object chatId) {

        //数据判空
        if (userId == null || chatId == null) {
            return new ServiceResult(Status.ERROR, ServiceMessage.PARAMETER_NOT_ENOUGHT.message, null);
        }
        File file;
        try {
            file = new File("/upload/file/" + getUUID() + ".txt");
            messageDao.messageToFile(userId, chatId, file.getPath());
        } catch (DaoException e) {
            e.printStackTrace();
            return new ServiceResult(Status.ERROR, DATABASE_ERROR.message, chatId);
        }
        return new ServiceResult(Status.SUCCESS, null, file.getName());
    }


    /**
     * 获取一个用户的所有未读的消息
     *
     * @param userId 用户id
     * @param page   页数
     * @notice none
     */
    @Override
    public ServiceResult listAllUnreadMessage(Object userId, int page) {
        if (userId == null) {
            return new ServiceResult(Status.ERROR, ServiceMessage.PARAMETER_NOT_ENOUGHT.message, null);
        }
        List<MessageVO> messageVOList = new LinkedList<>();
        //一次最多获取2000条未读消息
        int limit = 2000;
        int offset = (page - 1) * limit;
        if (offset < 0) {
            return new ServiceResult(Status.ERROR, ServiceMessage.PAGE_INVALID.message, null);
        }
        try {
            User user = userDao.getUserById(userId);
            //检查用户是否存在
            if (user == null) {
                return new ServiceResult(Status.ERROR, ServiceMessage.ACCOUNT_NOT_FOUND.message, null);
            }
            List<Message> messageList = messageDao.listUnreadMessageByUserId(userId, limit, offset);
            //检查数据是否存在
            if (messageList == null || messageList.size() == 0) {
                return new ServiceResult(Status.SUCCESS, null, messageList);
            }
            //使用记录和用户信息建造视图层消息实体
            toMessageVOObject(messageVOList, messageList);
        } catch (DaoException e) {
            e.printStackTrace();
            return new ServiceResult(Status.ERROR, DATABASE_ERROR.message, null);
        }
        return new ServiceResult(Status.SUCCESS, null, messageVOList);
    }

    /**
     * 获取一个用户在一个聊天中的所有未读的消息
     *
     * @param userId 用户id
     * @param chatId 聊天id
     * @param page   页数
     * @name listUnreadMessage
     * @notice none
     */
    @Override
    public ServiceResult listUnreadMessage(Object userId, Object chatId, int page) {
        if (userId == null || chatId == null) {
            return new ServiceResult(Status.ERROR, ServiceMessage.PARAMETER_NOT_ENOUGHT.message, null);
        }
        List<MessageVO> messageVOList = new LinkedList<>();
        List<Message> list = null;
        int limit = 1000;
        int offset = (page - 1) * limit;
        if (offset < 0) {
            return new ServiceResult(Status.ERROR, ServiceMessage.PAGE_INVALID.message, null);
        }
        try {
            User user = userDao.getUserById(userId);
            //检查用户是否存在
            if (user == null) {
                return new ServiceResult(Status.ERROR, ServiceMessage.ACCOUNT_NOT_FOUND.message, null);
            }
            List<Message> messageList = messageDao.listUnreadMessage(userId, chatId, limit, offset);
            //检查数据是否存在
            if (messageList == null || messageList.size() == 0) {
                return new ServiceResult(Status.SUCCESS, ServiceMessage.NO_RECORD.message, messageList);
            }
            //使用记录和用户信息建造视图层消息实体
            toMessageVOObject(messageVOList, messageList);
        } catch (DaoException e) {
            e.printStackTrace();
            return new ServiceResult(Status.ERROR, DATABASE_ERROR.message, null);
        }
        return new ServiceResult(Status.SUCCESS, null, messageVOList);
    }

    /**
     * 将一条消息从一个用户的消息记录中移除，并不会删除这条消息
     *
     * @param userId    用户id
     * @param messageId 要移除的消息记录id
     * @name removeMessage
     * @notice 本方法没有将消息删除，只是将该用户的对应这条消息的记录删除，</br>
     * 其他拥有这条消息的记录的用户依然可以访问这条消息
     */
    @Override
    public ServiceResult removeMessage(BigInteger userId, BigInteger messageId) {
        if (userId == null | messageId == null) {
            return new ServiceResult(Status.ERROR, ServiceMessage.PARAMETER_NOT_ENOUGHT.message, null);
        }
        Record record = null;
        try {
            record = new Record();
            record.setUserId(userId);
            record.setMessageId(messageId);
            //将该记录移除
            if (recordDao.delete(record) != 1) {
                return new ServiceResult(Status.ERROR, DATABASE_ERROR.message, record);
            }
        } catch (DaoException e) {
            e.printStackTrace();
            return new ServiceResult(Status.ERROR, ServiceMessage.DATABASE_ERROR.message, record);
        }
        return new ServiceResult(Status.SUCCESS, ServiceMessage.OPERATE_SUCCESS.message, record);
    }

    /**
     * 删除一个用户在一个聊天中的所有记录
     *
     * @param userId 用户id
     * @param chatId 要移除的消息记录的聊天id
     * @name removeAllMessage
     * @notice 本方法没有将消息删除，只是将该用户的对应这条消息的记录删除，</br>
     * 其他拥有这条消息的记录的用户依然可以访问这条消息
     */
    @Override
    public ServiceResult removeAllMessage(BigInteger userId, BigInteger chatId) {
        if (userId == null | chatId == null) {
            return new ServiceResult(Status.ERROR, ServiceMessage.PARAMETER_NOT_ENOUGHT.message, null);
        }
        try {
            if (chatDao.getChatById(chatId) == null) {
                return new ServiceResult(Status.ERROR, ServiceMessage.CHAT_NOT_FOUND.message, null);
            }
            if (userDao.getUserById(userId) == null) {
                return new ServiceResult(Status.ERROR, ServiceMessage.ACCOUNT_NOT_FOUND.message, null);
            }
            //将记录移除
            recordDao.deleteAllRecordInChat(userId, chatId);
        } catch (DaoException e) {
            e.printStackTrace();
            return new ServiceResult(Status.ERROR, ServiceMessage.DATABASE_ERROR.message, chatId);
        }
        return new ServiceResult(Status.SUCCESS, ServiceMessage.OPERATE_SUCCESS.message, chatId);
    }

    /**
     * 撤回一条消息
     *
     * @param userId    用户id
     * @param messageId 消息id
     * @name drawMessage
     * @notice 这个方法会将消息删除
     */
    @Override
    public ServiceResult drawBackMessage(BigInteger userId, BigInteger messageId) {
        if (userId == null | messageId == null) {
            return new ServiceResult(Status.ERROR, ServiceMessage.PARAMETER_NOT_ENOUGHT.message, null);
        }
        try {
            Message message = new Message();
            message.setId(messageId);
            //将该消息移除
            if (messageDao.delete(message) != 1) {
                return new ServiceResult(Status.ERROR, DATABASE_ERROR.message, messageId);
            }
        } catch (DaoException e) {
            e.printStackTrace();
            return new ServiceResult(Status.ERROR, ServiceMessage.DATABASE_ERROR.message, messageId);
        }
        return new ServiceResult(Status.SUCCESS, ServiceMessage.OPERATE_SUCCESS.message, messageId);
    }


    /**
     * 将一个用户在一个聊天中收到的消息记录设置为已读
     *
     * @param userId 用户id
     * @param chatId 聊天id
     * @name setAlreadyRead
     * @notice none
     */
    @Override
    public void setAlreadyRead(Object userId, Object chatId) {
        try {
            recordDao.updateStatusInChat(1, userId, chatId);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    /**
     * 把一个装有数据库表实体类对象的集合和一个用户的信息，转化成一个视图层消息集合
     *
     * @param messageVOList 视图层对象集合
     * @param messageList   持久化层对象集合
     * @name toMessageVOObject
     * @notice none
     */
    public void toMessageVOObject(List<MessageVO> messageVOList, List<Message> messageList) {
        //使用记录和用户信息建造视图层消息实体
        for (Message message : messageList) {
            User sender = userDao.getUserById(message.getSenderId());
            if (sender == null) {
                throw new ServiceException("消息的发送者不存在");
            }
            MessageVO messageVo = new MessageVOBuilder().setSenderId(message.getSenderId())
                    .setSenderName(sender.getName()).setChatId(message.getChatId())
                    .setContent(message.getContent()).setSenderPhoto(sender.getPhoto())
                    .setTime(message.getTime()).setType(message.getType()).build();
            messageVOList.add(messageVo);
        }
    }

}
