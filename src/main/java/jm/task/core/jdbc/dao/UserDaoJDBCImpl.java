package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final static Connection connection = Util.getConnection();               //create connect via Util

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        final String createSqlQuery = "CREATE TABLE IF NOT EXISTS users (id INT primary key AUTO_INCREMENT," +
                "name VARCHAR(60) NOT NULL," +
                "last_name VARCHAR(60) NOT NULL," +
                "age TINYINT NOT NULL)";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(createSqlQuery);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Connection ERROR\n");
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void dropUsersTable() {
        final String dropSqlQuery = "drop table if exists users";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(dropSqlQuery);

        } catch (SQLException e) {
            System.out.println("Connection ERROR\n");
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        final String saveSqlQuery = "INSERT INTO users (name, last_name, age) values(?, ?, ?)";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(saveSqlQuery);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User по имени - " + name + " добавлен в базу данных.");

        } catch (SQLException e) {
            System.out.println("Connection ERROR\n");
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void removeUserById(long id) {
        final String removeSqlQuery = "DELETE FROM users WHERE id = " + id;
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(removeSqlQuery);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("CONNECTION ERROR\n");
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<User> getAllUsers() {
        final String getSqlQuery = "SELECT * FROM users";
        List<User> usersList = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(getSqlQuery);

            ResultSet result =  preparedStatement.executeQuery();
            while (result.next()) {
                User user = new User();
                user.setId(result.getLong(1));
                user.setName(result.getString(2));
                user.setLastName(result.getString(3));
                user.setAge(result.getByte(4));
                usersList.add(user);
            }
        } catch (SQLException e) {
            System.out.println("CONNECTION ERROR\n");
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return usersList;
    }

    public void cleanUsersTable() {
        final String cleanSqlQuery = "TRUNCATE mydbtest.users;";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(cleanSqlQuery);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("CONNECTION ERROR\n");
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void closeConnection() {
        try {
           connection.close();
            System.out.println("\nConnection via Util was dropped");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
