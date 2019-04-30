package com.mada.commons.utils.mysql;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by madali on 2019/3/20 16:11
 */
@Slf4j
public class DbUtil {

    private static final String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    // 可改为走配置文件获取
    private static final String URL = "jdbc:mysql://localhost:3306/test1";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "123456";

    private static BasicDataSource basicDataSource = new BasicDataSource();

    static {
        //创建数据源
        basicDataSource.setUrl(URL);
        basicDataSource.setDriverClassName(DRIVER_CLASS_NAME);
        basicDataSource.setUsername(USERNAME);
        basicDataSource.setPassword(PASSWORD);
        basicDataSource.setInitialSize(30);
        basicDataSource.setMinIdle(5);
        basicDataSource.setMaxIdle(20);

        //下面两条解决：使用DBCP连接池时出现MySql 8小时断开连接的问题
        basicDataSource.setTestOnBorrow(true);
        basicDataSource.setValidationQuery("select 1");
    }

    private static Connection getConnection() {
        Connection connection;

        try {
            connection = basicDataSource.getConnection();
        } catch (SQLException e) {
            try {
                connection = basicDataSource.getConnection();
            } catch (SQLException e1) {
                log.warn("获取MySQL连接失败.e:{}", e.getMessage());
                e1.printStackTrace();
                return null;
            }
        }

        return connection;
    }

    private static final String selectSql = "select * from t_wmb_record_entity order by updateTime asc limit 100";
    private static final String prefix_insertSql = "insert into t_wmb_record_entity (infoId,houseId,updateTime) values(";
    private static final String prefix_deleteSql = "delete from t_wmb_record_entity where id=";

    private static String getDateStr(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    private static String getDeleteRecordSql(int id) {
        return prefix_deleteSql + id;
    }

    // 查询
    public static List<WmbRecordEntity> getRecordList() {
        Connection connection = getConnection();
        List<WmbRecordEntity> list = new ArrayList<>();
        try {
            log.info("查询表t_wmb_record_entity的sql:{}", selectSql);
            PreparedStatement preparedStatement = connection.prepareStatement(selectSql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                WmbRecordEntity entity = WmbRecordEntity.builder().build();
                entity.setId(resultSet.getInt("id"));
                entity.setInfoId(resultSet.getLong("infoId"));
                entity.setHouseId(resultSet.getLong("houseId"));

                String updateTimeStr = resultSet.getString("updateTime");
                Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(updateTimeStr);
                entity.setUpdateTime(date);

                list.add(entity);
            }

            resultSet.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            doFinally(connection);
        }

        return list;
    }

    // 删除
    public static void deleteRecord(int id) {
        Connection connection = getConnection();
        try {
            String deleteSql = getDeleteRecordSql(id);
            log.info("删除t_wmb_record_entity的sql:{}", deleteSql);
            PreparedStatement preparedStatement = connection.prepareStatement(deleteSql);
            int result = preparedStatement.executeUpdate();
            log.info("删除表t_wmb_record_entity中的记录{},删除结果:{}", id, result);
        } catch (SQLException e) {
            rollback(connection);
        } finally {
            doFinally(connection);
        }
    }

    private static String getInsertSql(WmbRecordEntity entity) {
        if (entity.getUpdateTime() == null) {
            entity.setUpdateTime(new Date());
        }

        return prefix_insertSql +
                entity.getInfoId() + "," +
                entity.getHouseId() + ",'" +
                getDateStr(entity.getUpdateTime()) + "')";
    }

    // 写入
    public static void insertRecord(WmbRecordEntity entity) {
        Connection connection = getConnection();
        try {
            String insertSql = getInsertSql(entity);
            log.info("写入t_wmb_record_entity的sql:{}", insertSql);
            PreparedStatement preparedStatement = connection.prepareStatement(insertSql);
            int result = preparedStatement.executeUpdate();
            log.info("写入表t_wmb_record_entity的infoId:{},写入结果:{}", entity.getInfoId(), result);
        } catch (SQLException e) {
            rollback(connection);
        } finally {
            doFinally(connection);
        }
    }

    private static void doFinally(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 回滚
    private static void rollback(Connection connection) {
        if (connection != null) {
            try {
                if (!connection.getAutoCommit()) {
                    //手动回滚
                    connection.rollback();
                }
            } catch (SQLException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

}