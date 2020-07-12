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
    private static SessionFactory sessionFactory = null;

    static {
        try {
            Configuration configuration = getConfig();
            sessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    public static Configuration getConfig() {
        Configuration configuration = new Configuration()
                .setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver")
                .setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/testDB?useSSL=false")
                .setProperty("hibernate.connection.username", "testUser")
                .setProperty("hibernate.connection.password", "testUser")
                .setProperty("hibernate.connection.autocommit", "false")
                .setProperty("hibernate.cache.provider_class", "org.hibernate.cache.NoCacheProvider")
                .setProperty("hibernate.cache.use_second_level_cache", "false")
                .setProperty("hibernate.cache.use_query_cache", "false")
                .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
                .setProperty("hibernate.show_sql", "true")
                .setProperty("hibernate.current_session_context_class", "thread")
                //.addPackage("ru.miralab.db")
                .addAnnotatedClass(jm.task.core.jdbc.model.User.class);
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
