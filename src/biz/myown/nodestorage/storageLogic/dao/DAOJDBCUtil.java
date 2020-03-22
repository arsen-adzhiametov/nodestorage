package biz.myown.nodestorage.storagelogic.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DAOJDBCUtil {

    public static void close(Statement statement) throws SQLException {
        if (statement != null) {

            statement.close();
        }
    }

    public static void close(ResultSet resultSet) throws SQLException {
        if (resultSet != null) {

            resultSet.close();

        }
    }

    public static void close(Connection connection) throws SQLException {
        if (connection != null) {

            connection.close();


        }
    }
}
