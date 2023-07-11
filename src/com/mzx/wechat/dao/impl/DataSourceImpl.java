
package com.mzx.wechat.dao.impl;

import com.mzx.wechat.dao.DataSource;
import com.mzx.wechat.exception.DaoException;
import com.mzx.wechat.factory.proxy.ConnectionProxy;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @program wechat
 * @description 负责向Dao层提供数据库连接
 */
public class DataSourceImpl implements DataSource {
    /*
     **************************************************************
     *            加载配置文件，注册数据库驱动
     **************************************************************
     */
    /**
     * 配置文件路径
     */
    private static final String PROP_PATH = "data_source.properties";
    private static Properties prop = new Properties();

    static {
        try {
            //加载配置文件

            prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(PROP_PATH));
            String driver = prop.getProperty("driver");
            //注册驱动
            Class.forName(driver);
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        }
    }

    /*
     **************************************************************
     *            初始化数据库连接池配置
     **************************************************************
     */
    /**
     * 测试数据库连接的等待时长
     */
    private static int TIMEOUT = Integer.parseInt(prop.getProperty("TIMEOUT"));
    /**
     * 初始数据库连接数
     */
    private static int INIT_SIZE = Integer.parseInt(prop.getProperty("INIT_SIZE"));
    /**
     * 最大数据库连接数
     */
    private static int MAX_SIZE = Integer.parseInt(prop.getProperty("MAX_SIZE"));
    /**
     * 当前已经创建的数据库连接数
     */
    private static int currentCount = 0;
    /**
     * 数据库连接链接
     */
    private static String url = prop.getProperty("url");
    /**
     * 数据库连接用户
     */
    private static String user = prop.getProperty("user");
    /**
     * 数据库连接密码
     */
    private static String password = prop.getProperty("password");

    /*
     **************************************************************
     *            创建数据源和数据库连接池实例
     **************************************************************
     */

    /**
     * 这里使用饿汉式单例模式
     */
    private static DataSourceImpl instance = new DataSourceImpl();
    /**
     * 数据库连接池
     */
    private final Queue<Connection> connPool = new LinkedBlockingQueue<>();

    /**
     * 负责提供数据库连接池实例
     *
     * @return com.mzx.www.dao.MyDataSource
     * @name getInstance
     */
    public static DataSourceImpl getInstance() {
        if (instance != null) {
            return instance;
        }else {
            return new DataSourceImpl();
        }
    }

    /**
     * 创建连接池实例，初始化数据库连接池
     *
     * @name MyDataSource
     */
    private DataSourceImpl() {
        for (int i = 0; i < INIT_SIZE; i++) {
            this.connPool.add(this.createConnection());
        }
    }

    /*
     **************************************************************
     *            提供接口中数据库连接的操作方法
     **************************************************************
     */

    /**
     * 负责从数据库连接池中获取数据库连接
     *
     * @return java.sql.Connection
     * @throws DaoException 如果数据库连接已经达到最大值时仍然调用此方法，则抛出此异常
     * @name getConnection
     * @notice 数据库连接的数量受到配置文件中最大值的限制
     */
    @Override
    public Connection getConnection() throws DaoException {
        Logger log = Logger.getLogger(DataSourceImpl.class);
        log.info("连接池已创建连接数" + getCurrentCount() + "空闲连接数" + getfreeCount());
        synchronized (connPool) {
            if (connPool.size() > 0) {
                /**
                 * 先检查连接是否可用，如果不可用，关闭该连接，返回一个新连接
                 */
                Connection conn = connPool.remove();
                try {
                    if (conn.isValid(TIMEOUT)) {
                        return new ConnectionProxy(this).getProxyInstance(conn);
                    } else {
                        destroyConnection(conn);
                        return createConnection();
                    }
                } catch (SQLException e) {
                    throw new DaoException("测试数据库连接产生异常", e);
                }
            } else if (currentCount < MAX_SIZE) {
                Connection conn = createConnection();
                return new ConnectionProxy(this).getProxyInstance(conn);
            } else {
                throw new DaoException("数据库连接数已达到最大值");
            }
        }
    }


    /**
     * 用于将数据库连接放回连接池中
     *
     * @param conn 数据库连接
     * @name freeConnection
     */
    @Override
    public void freeConnection(Connection conn) {
        this.connPool.add(conn);
    }

    /**
     * 关闭数据库连接
     *
     * @return java.sql.Connection
     * @name createConnection
     */
    @Override
    public void destroyConnection(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            throw new DaoException("关闭数据库连接异常", e);
        }
    }

    /**
     * 负责返回当前已经创建的连接数
     *
     * @return int 当前已经创建的连接数
     * @name getCurrentCount
     */
    @Override
    public int getCurrentCount() {
        return currentCount;
    }

    /**
     * 负责返回当前池中剩余的空闲连接数
     *
     * @return int 当前空闲连接数
     * @name getfreeCount
     */
    @Override
    public int getfreeCount() {
        return this.connPool.size();
    }


    /*
     **************************************************************
     *            私有方法
     **************************************************************
     */

    /**
     * 创建连接("协议+访问的数据库名”，“用户名”，“密码”）
     *
     * @return java.sql.Connection
     * @name createConnection
     */
    private Connection createConnection() throws DaoException {
        currentCount++;
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("无法创建数据库连接：" + e.getMessage(), e);
        }
    }


}
