
package com.mzx.wechat.util;

import com.mzx.wechat.dao.DataSource;
import com.mzx.wechat.dao.impl.DataSourceImpl;
import com.mzx.wechat.exception.DaoException;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * @program XHotel
 * @description 用于从数据库连接池中获取连接和将连接放回数据库连接池
 */
public class JdbcUtils {

    private static DataSource dataSrc = DataSourceImpl.getInstance();
    private final static String PROP_PATH = "db_config.properties";

    private JdbcUtils() {
    }


    /**
     * 负责从数据库连接池中获取数据库连接
     *
     * @return java.sql.Connection
     * @name getConnection
     * @notice 数据库连接的数量受到配置文件中最大值的限制
     */
    public static Connection getConnection() {
        return dataSrc.getConnection();
    }


    /**
     * 用于将数据库连接放回连接池中,并释放Statement和ResultSet的资源
     *
     * @param conn 数据库连接
     * @name close
     */
    public static void close(ResultSet rs, Statement st, Connection conn) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (st != null) {
                st.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        close(conn);
    }

    /**
     * 用于将一个数据库连接放回连接池中
     *
     * @param conn 数据库连接
     * @name close
     */
    public static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 负责返回当前池中剩余的空闲连接数
     *
     * @return int 当前空闲连接数
     * @name getfreeCount
     */
    public static int getfreeCount() {
        return dataSrc.getfreeCount();
    }


    /**
     * 负责返回当前已经创建的连接数
     *
     * @return int 当前已经创建的连接数
     * @name getCurrentCount
     */
    public static int getCurrentCount() {
        return dataSrc.getCurrentCount();
    }

    /**
     * 负责根据传入的参数数组给PreparedStatement填入参数
     *
     * @param ps     需要设参的预编译sql语句
     * @param params 参数数组
     * @name setParams
     */
    public static void setParams(PreparedStatement ps, Object[] params) {
        for (int i = 0; i < params.length; i++) {
        }
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                try {
                    ps.setObject(i + 1, params[i]);
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new DaoException("预编译参数异常：" + ps.toString(), e);
                }
            }
        }
    }


    /**
     * 负责返回对象对应的表名
     *
     * @param obj 查询表名的对象
     * @return java.lang.String
     * @name getTableName
     * @notice none
     */
    public static String getTableName(Object obj) {
        return obj == null ? null : getConfig(obj.getClass().getSimpleName());
    }


    /**
     * 负责返回该类对应的表名
     *
     * @param clazz 查询表名的类
     * @return java.lang.String
     * @name getTableName
     * @notice none
     */
    public static String getTableName(Class clazz) {
        return clazz == null ? null : getConfig(clazz.getSimpleName());
    }


    /**
     * 负责返回该表名对应的类
     *
     * @param tableName 表名
     * @return java.lang.Class
     * @name getClass
     * @notice none
     */
    public static Class getClass(String tableName) {
        try {
            return tableName == null ? null : Class.forName(getConfig(tableName));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new DaoException("无法加载表名对应的类:" + tableName, e);
        }
    }


    /**
     * 负责加载配置文件，向Dao层提供配置信息
     *
     * @param key 配置文件的键
     * @return java.lang.String 配置文件的值
     * @name getConfig
     * @notice none
     */
    public static String getConfig(String key) {

        try {
            Properties prop = new Properties();
            prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(PROP_PATH));
            return key == null ? null : prop.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
            throw new DaoException("无法加载配置文件:" + PROP_PATH, e);
        }
    }

}
