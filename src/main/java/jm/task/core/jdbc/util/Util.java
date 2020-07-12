package jm.task.core.jdbc.util;

import java.sql.*;

public class Util {
    // реализуйте настройку соеденения с БД
    public static Statement conDB() {
        Connection connection;
        Statement statement = null;
        String url = "jdbc:mysql://localhost:3306/testDB?useSSL=false";
        try {
            connection = DriverManager.getConnection(url, "testUser", "testUser");
            statement = connection.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return statement;
    }
}
