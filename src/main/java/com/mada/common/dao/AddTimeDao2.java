//package com.common.dao;
//import AddTime;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by madali on 2017/4/27.
// */
//public class AddTimeDao2 {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(AddTimeDao2.class);
//
//    private static final String DB_NAME = Configuration.AGGREGATE_DB_NAME;
//    private static final String URL = ZkUtil.getInfrastructure(InfrastructureEnum.MYSQL, "url").getValue();
//    private static final String USERNAME = ZkUtil.getInfrastructure(InfrastructureEnum.MYSQL, "username").getValue();
//    private static final String PASSWORD = ZkUtil.getInfrastructure(InfrastructureEnum.MYSQL, "password").getValue();
//
//    private static final String LIST_ADDTIME_SQL = "select * from add_time";
//    private static final String CREATE_ADDTIME_TABLE_SQL = "CREATE TABLE IF NOT EXISTS add_time " +
//            "(" +
//            "`id` INT UNSIGNED NOT NULL, " +
//            "`add_day` INT, " +
//            "`add_hour` INT, " +
//            "`add_minute` INT, " +
//            "PRIMARY KEY (`id`)" +
//            ") " +
//            "ENGINE=InnoDB DEFAULT CHARSET=utf8;";
//
//    public final void createTable() {
//
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//
//        try {
//            initDatabase();
//            connection = DbUtil.getConnection(DB_NAME);
//            preparedStatement = connection.prepareStatement(CREATE_ADDTIME_TABLE_SQL);
//
//            preparedStatement.execute();
//            LOGGER.info("Execute sql: {}", CREATE_ADDTIME_TABLE_SQL);
//
//        } catch (Throwable t) {
//            throw new RuntimeException(t);
//        } finally {
//
//            if (preparedStatement != null)
//                try {
//                    preparedStatement.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//
//            if (connection != null) {
//                try {
//                    connection.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//
//        }
//    }
//
//    public List<AddTime> list() {
//
//        String dbName = Configuration.AGGREGATE_DB_NAME;
//
//        List<AddTime> addTimeList = new ArrayList<>();
//
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//
//        try {
//            initDatabase();
//            connection = DbUtil.getConnection(dbName);
//            preparedStatement = connection.prepareStatement(LIST_ADDTIME_SQL);
//
//            ResultSet resultSet = preparedStatement.executeQuery();
//
//            while (resultSet.next()) {
//
//                AddTime addTime = new AddTime();
//                addTime.setAddDay(resultSet.getInt(2));
//                addTime.setAddHour(resultSet.getInt(3));
//                addTime.setAddMinute(resultSet.getInt(4));
//
//                addTimeList.add(addTime);
//            }
//
//        } catch (Throwable t) {
//            throw new RuntimeException(t);
//        } finally {
//
//            if (preparedStatement != null)
//                try {
//                    preparedStatement.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//
//            if (connection != null) {
//                try {
//                    connection.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//
//        }
//
//        return addTimeList;
//    }
//
//    private void initDatabase() {
//        //初始化数据库
//        Configuration.configureDataSource(DataSourceEnum.MYSQL, new ConnectionEntity(URL, USERNAME, PASSWORD));
//    }
//}
