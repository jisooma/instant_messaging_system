

package com.mzx.wechat.service.constants;

/**
 * @description 用于描述错误信息
 */
public enum ServiceMessage {

    /*
     **************************************************************
     *              用户服务错误消息
     **************************************************************
     */


    /**
     * 邮箱格式不合法
     */
    EMAIL_FORMAT_INCORRECT("您输入的邮箱格式不正确，邮箱只能由英文字母/数字组成,且域名符合规则"),
    /**
     * 邮箱已被注册
     */
    EMAIL_ALREADY_USED("该邮箱已被注册！"),
    /**
     * 密码不正确
     */
    PASSWORD_INCORRECT("您输入的密码不正确，请重新输入或尝试找回密码"),

    /**
     * 账号不存在
     */
    ACCOUNT_NOT_FOUND("该账号不存在，请检查您的输入是否正确"),
    /**
     * 微信号已存在
     */
    WECHAT_ID_USED("您输入的微信号已被占用，请重新输入"),
    /**
     * 微信号不合法
     */
    WECHAT_ID_INVALID("您输入的微信号不符合要求，请重新输入"),
    /**
     * 密码不符合要求
     */
    INVALID_PASSWORD("您的密码不符合要求，密码必须是6~20位英文字母/数字/下划线组成"),
    /**
     * 找不到用户信息
     */
    NO_USER_INFO("找不到该用户的信息，请检查该用户是否登陆"),
    /**
     * 更新用户信息失败
     */
    UPDATE_USER_FAILED("更新用户信息失败，请重试"),
    /**
     * 找不到相关用户
     */
    NO_SUCH_USER("找不到相关用户，试着换一个关键词吧"),

    /*
     **************************************************************
     *              用户服务成功消息
     **************************************************************
     */
    /**
     * 注册信息符合要求
     */
    REGISTER_INFO_VALID("您的注册信息符合要求，可以继续注册"),
    /**
     * 注册成功
     */
    REGISTER_SUCCESS("您已经注册成功，请记住您的密码，使用邮箱号进行登陆"),

    /**
     * 登陆成功
     */
    LOGIN_SUCCESS("登陆成功，欢迎进入ours wechat在线聊天系统"),
    /**
     * 退出登陆成功
     */
    LOGOUT_SUCCESS("您已成功退出账号，请重新登陆"),

    /**
     * 用户名有效
     */
    WECHAT_ID_VALID("该微信号有效且未被占用"),
    /**
     * 获取个人信息成功
     */
    GET_INFO_SUCCESS("获取个人信息成功"),
    /**
     * 更新个人信息成功
     */
    UPDATE_INFO_SUCCESS("更新个人信息成功，请刷新页面查看"),
    /**
     * 更新密码成功
     */
    UPDATE_PASSWORD_SUCCESS("您已成功更新登陆密码，新的登陆密码为 ："),
    /**
     * 更新聊天背景
     */
    UPDATE_BACKGROUND_SUCCESS("更新聊天背景成功，打开一个聊天窗口即可看到效果"),


    /*
     **************************************************************
     *              聊天服务失败消息
     **************************************************************
     */
    /**
     * 发起聊天失败
     */
    CREATE_CHAT_FAILED("发起聊天失败，您无法与对方进行聊天"),
    /**
     * 加入聊天失败
     */
    JOIN_CHAT_FAILED("加入聊天失败"),

    /**
     * 用户已存在于聊天
     */
    MEMBER_ALREADY_EXIST("该成员已经在此聊天中，不可重复添加"),
    /**
     * 聊天中没有该成员
     */
    MEMBER_NOT_FOUND("此聊天中没有该成员"),
    /**
     * 退出聊天失败
     */
    QUIT_CHAT_FAILED("退出聊天失败，请重试"),
    /**
     * 不是群主
     */
    NOT_OWNER("您并不是该群的群主或管理员，没有权限进行此操作"),

    /*
     **************************************************************
     *              聊天服务成功消息
     **************************************************************
     */
    /**
     * 创建聊天成功
     */
    CREATE_CHAT_SUCCESS("创建聊天成功"),
    /**
     * 创建群聊成功
     */
    CREATE_GROUP_CHAT_SUCCESS("你成功创建了一个群聊，群号为 :"),
    /**
     * 提示邀请好友加群
     */
    PLEASE_JOIN_CHAT(" 使用群号即可加入群聊,快去邀请好友加群吧"),
    /**
     * 加入聊天成功
     */
    JOIN_CHAT_SUCCESS("加入聊天成功"),
    /**
     * 退出聊天成功
     */
    QUIT_CHAT_SUCCESS("退出聊天成功，以后你将收不到该聊天的任何信息"),
    /**
     * 移除聊天成员成功
     */
    REMOVE_MEMBER_SUCCESS("移除聊天成员成功"),
    /**
     * 移除聊天成员失败
     */
    REMOVE_MEMBER_FAILED("移除聊天成员失败"),
    /**
     * 不可移除好友
     */
    CANNOT_REMOVE_FRIEND("该成员所处的聊天是好友私聊，不可移除，您可以选择删除该好友"),
    /**
     * 当前没有聊天
     */
    NO_CHAT_NOW("您当前没有加入任何聊天，请先添加好友或者加入群聊吧"),
    /**
     * 查找所有聊天成功
     */
    LIST_CHAT_SUCCESS("成功加载聊天列表"),
    /**
     * 群号不合法
     */
    CHAT_NUMBER_INVALID("您输入的群号不符合要求，群号必须只由数字组成，并且长度在6-20位之间"),
    /**
     * 群号已存在
     */
    CHAT_NUMBER_ALREADT_EXIST("您输入的群号已经被占用，请重新输入"),
    /**
     * 加入群聊成功
     */
    JOIN_GROUP_CHAT_SUCCESS("加入群聊成功,请在聊天列表查看"),
    /**
     * 聊天不存在
     */
    CHAT_NOT_FOUND("该聊天不存在，请检查您的输入"),

    /*
     **************************************************************
     *              上传服务消息
     **************************************************************
     */
    /**
     * 上传文件失败
     */
    UPLOAD_FAILED("上传文件失败，请重试"),
    /**
     * 上传成功
     */
    UPLOAD_SUCCESS("上传文件成功"),
    /**
     * 不支持的上传操作
     */
    UNSUPPROT_TABLE("系统尚未提供这张数据库表的上传服务"),
    /*
     **************************************************************
     *             好友服务消息
     **************************************************************
     */
    /**
     * 添加好友成功
     */
    ADD_FRIEND_SUCCESS("发送好友申请成功，当对方同意好友申请时，就可以和对方开始聊天了"),
    /**
     * 不可重复添加好友
     */
    ALREADY_ADD_FRIEND("您已经发出了好友申请，不可重复添加好友，没有看到聊天窗口的话，可以刷新浏览器试一试哦"),
    /**
     * 当前没有好友
     */
    NO_FRIEND_NOW("您现在还没有好友呢，快去添加好友吧"),
    /**
     * 删除好友成功
     */
    DELETE_FRIEND_SUCCESS("对方已从您的好友中移除，将不再接收对方的消息"),
    /**
     * 该好友不存在
     */
    FRIEND_NOT_EXIST("系统找不到该好友的账号"),
    /**
     * 更新好友信息成功
     */
    UPDATE_FRIEND_SUCCESS("更新好友信息成功"),
    /**
     * 已经是好友
     */
    ALREADY_FRIEND("该用户已经是你的好友了，不可重复添加，去你的好友列表里找他吧"),
    /**
     * 不可加自己为好友
     */
    CANNOT_ADD_YOURSELF("不可以添加自己为好友哦"),
    /**
     * 同意好友申请
     */
    AGREE_FRIEND("我通过了你的好友验证请求，现在我们可以开始聊天了"),
    /**
     * 不可删除系统账号
     */
    CANNOT_DELETE_SYSTEM("这是系统账号，负责提供消息推送功能，不可以被删除"),
    /**
     * 不可删除开发者账号
     */
    CANNOT_DELETE_HYC("这是开发者账号，他掌握着本系统的后台数据库，所以你是删不掉的哈哈哈"),
    /*
     **************************************************************
     *             聊天记录服务消息
     **************************************************************
     */
    /**
     * 无法删除记录
     */
    DELETE_FAILED("无法将该聊天记录删除，请重试"),
    /**
     * 没有聊天记录
     */
    NO_RECORD("当前没有聊天记录"),
    /**
     * 已将消息设置为已读
     */
    ALREADY_READ("您打开了一个窗口，系统已将其中已将其中的消息设置为已读，如果没有新消息，下次加载时一个聊天最多只加载20条已读消息"),
    /*
     **************************************************************
     *             聊天记录服务消息
     **************************************************************
     */
    /**
     * 内容数据不合法
     */
    CONTENT_ILLEGAL("系统检测到您输入的内容中包含非法字符，已自动将其过滤 "),

    /**
     * 内容过长
     */
    CONTENT_TOO_LONG("您输入的内容过长，请重新输入"),

     /**
     * 没有更多
     */
    NO_MORE("没有更多数据了"),

    /**
     * 缺少时间参数
     */
    MISSING_TIME("该消息中缺少了发送时间参数，无法存入数据库"),
    /**
     * 缺少类型参数
     */
    MISSING_TYPE("该消息中缺少了类型参数，无法存入数据库"),


    /**
     * 发布成功
     */
    POST_SUCCESS("发布成功"),
    /**
     * 发布成功
     */
    POST_FAILED("发布失败"),

    /*
     **************************************************************
     *              数据检查提示
     **************************************************************
     */
    /**
     * 内容不能为空
     */
    NOT_NULL("内容不能为空"),
    /**
     * 记录不存在
     */
    NOT_FOUND("该记录不存在"),
    /**
     * 页数小于0
     */
    PAGE_INVALID("无法查询页数小于1的记录,该查询无效"),

    /*
     **************************************************************
     *              系统消息
     **************************************************************
     */
    /**
     * 参数不足
     */
    PARAMETER_NOT_ENOUGHT("请求参数不足，无法执行服务"),
    /**
     * 请求重试
     */
    PLEASE_REDO("执行失败，请重试"),

    /**
     * 操作成功
     */
    OPERATE_SUCCESS("执行操作成功"),

    /**
     * 系统故障
     */
    DATABASE_ERROR("系统数据库发生了故障，无法正常提供服务");


    public String message;

    ServiceMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    private void setMessage(String message) {
        this.message = message;
    }
}