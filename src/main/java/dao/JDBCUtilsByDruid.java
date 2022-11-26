package dao;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * 涉及数据库操作的都以此类为父类
 * 类被加载的时候自动生成连接池
 */
public class JDBCUtilsByDruid {
    private static DataSource ds;
    static {//静态代码初始化ds
        Properties properties = new Properties();
        try {
            InputStream fis =JDBCUtilsByDruid.class.getClassLoader().getResourceAsStream("db.properties");
            properties.load(fis);
            ds = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //获取连接
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
    //在数据库连接池技术中，close 不是真的断掉连接,而是把使用的 Connection 对象放回连接池
    public static void close(ResultSet resultSet, Statement statement, Connection connection) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

