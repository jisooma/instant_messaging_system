# instant_messaging_system
安全即时通讯系统   javaweb + mysql

本即时通信系统分为6个模块，分别是登录注册模块，用户管理模块，搜索添加好友模块，群聊模块，好友管理模块，加密模块，日志管理模块组成。

登录注册模块：用户可以输入邮箱和密码进行注册。如果用户已经有了注册了账号，则用户可以在前端界面输入自己的邮箱和密码登录，登录成功之后，则可以进入相关的菜单功能界面。

用户管理：用户可以对自身的账号信息进行管理，当点击头像时，可以显示出个人信息，可以对用户的信息进行修改，比如修改昵称，个性签名等等，也可以对自己的密码进行修改。

查找添加好友模块：用户可以在搜索栏搜索好友信息，搜索成功之后，会显示在页面，用户可以点击添加好友按钮，实现好友的添加。

群聊模块：可实现群聊的创建，用户添加群聊，用户退出群聊，如果需要给群聊成员发消息，直接在通讯录找见对应的群聊信息，点击发消息，即可以实现群聊交互。当群聊有多个成员时，发送消息，该群聊的所有成员都可以收到信息。

好友管理：用户可以对好友进行管理，可以与好友聊天，删除好友，修改好友信息。好友之间的聊天为私聊，当需要发送消息时，直接在通讯录找见对应的好友，点击发消息，即可以和好友聊天。用户可以在通讯录找见好友，点击好友的头像，即可以对好友进行备注，描述的修改等等。点击删除好友，即可以删除好友。

加密模块：需要对用户的密码进行加密，需要对用户发送的消息进行加密，加密的密钥可以放在数据库进行管理。每个群聊都共用同一个密钥对。密钥保存在每个用户中，成员在发送消息时，用公用的公钥进行加密，收到消息的人用私钥解密。

日志管理：通过Apache的log4j包实现，通过使用 Log4j ，我们可以控制日志信息输送的目的地是控制台、文件、 GUI 组件等等。Log4j 由三个重要的组件构成：日志信息的优先级，日志信息的输出目的地，日志信息的输出格式 。日志信息的优先级从高到低有 ERROR 、 WARN 、 INFO 、 DEBUG ，分别用来指定这条日志信息的重要程度；日志信息的输出目的地指定了日志将打印到控制台还是文件，例如org.apache.log4j.ConsoleAppender（控制台）和org.apache.log4j.FileAppender （文件）；而输出格式则控制了日志信息的显示内容。


