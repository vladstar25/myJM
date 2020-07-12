package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import sun.misc.ExtensionInstallationException;


import java.sql.*;

public class Util {
    // Для Hibernet
    private static SessionFactory sessionFactory;

    static {
        try {
            Configuration configuration = getConfig();
            configuration.configure();
            sessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    public static Configuration getConfig() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(User.class);
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        configuration.setProperty("connection.driver_class", "com.mysql.jdbc.Driver");
        configuration.setProperty("connection.url", "jdbc:mysql://localhost:3306/testDB?useSSL=false");
        configuration.setProperty("connection.username", "testUser");
        configuration.setProperty("connection.password", "testUser");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.hbm2ddl.auto", "update");
        return configuration;
    }

    public static Session getSession () {
        return sessionFactory.openSession();
    }

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
