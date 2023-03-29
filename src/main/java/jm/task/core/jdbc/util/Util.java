package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/mydbtest";
    private static final String DB_USERNAME = "Tugulka";
    private static final String DB_PASSWORD = "Vladika23.";


    private static Configuration hibernateCfg(){
        Configuration configuration = new Configuration();

        final Properties PROPERTIES = new Properties();

        PROPERTIES.put("hibernate.connection.driver_class", DB_DRIVER);
        PROPERTIES.put("hibernate.connection.url", DB_URL);
        PROPERTIES.put("hibernate.connection.username", DB_USERNAME);
        PROPERTIES.put("hibernate.connection.password", DB_PASSWORD);
        PROPERTIES.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        PROPERTIES.put("hibernate.show_sql", true);
        PROPERTIES.put("hbm2ddl.auto", "create-drop");
        PROPERTIES.put("hibernate.current_session_context_class", "thread");
        configuration.addProperties(PROPERTIES).addAnnotatedClass(User.class);

        return configuration;
    }

    public static SessionFactory createSession() {
        return hibernateCfg().buildSessionFactory();
    }




    public static Connection getConnection() {
        Connection connection = null;

        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            System.out.println("SUCCESS CONNECTION");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("CONNECTION IS NOT ESTABLISHED");
        }
        return connection;

    }
}
