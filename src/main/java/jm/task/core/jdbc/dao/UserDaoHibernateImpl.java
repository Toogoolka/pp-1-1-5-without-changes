package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class UserDaoHibernateImpl implements UserDao {
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_YELLOW = "\u001B[1;33m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String RESET = "\033[0m";
    private final SessionFactory FACTORY = Util.createSession();
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try {
            Session session = FACTORY.getCurrentSession();
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery("CREATE TABLE IF NOT EXISTS users (id INT primary key AUTO_INCREMENT," +
                    "name VARCHAR(60) NOT NULL," +
                    "last_name VARCHAR(60) NOT NULL," +
                    "age TINYINT NOT NULL)").addEntity(User.class);
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            if(transaction != null) {
                transaction.rollback();
            }
            System.out.println(ANSI_YELLOW + "ТРАНЗАКЦИЯ ОТМЕНЕНА " +
                    ANSI_BLUE + "Произошла ошибка во время операции." + RESET);
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try {
            Session session = FACTORY.getCurrentSession();
            transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE if EXISTS users").executeUpdate();
            session.getTransaction().commit();
            System.out.println(ANSI_RED + "\t\t*** БАЗА ДАННЫХ УНИЧТОЖЕНА ***" + RESET);
        } catch (Exception e) {
            if(transaction != null) {
                transaction.rollback();
            }
            System.out.println(ANSI_YELLOW + "ТРАНЗАКЦИЯ ОТМЕНЕНА " +
                    ANSI_BLUE + "Произошла ошибка во время операции." + RESET);
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try{
            Session session = FACTORY.getCurrentSession();
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
            System.out.println(ANSI_YELLOW + "\t\t*** ТРАНЗАКЦИЯ ЗАВЕРШЕНА ***\n" + RESET +
                    "User по имени " + ANSI_BLUE + name + RESET + " добавлен в базу");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                System.out.println(ANSI_YELLOW + "ТРАНЗАКЦИЯ ОТМЕНЕНА " +
                        ANSI_BLUE + "Произошла ошибка во время операции." + RESET);
                e.printStackTrace();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try{
            Session session = FACTORY.getCurrentSession();
            transaction = session.beginTransaction();
            session.createSQLQuery("delete from users where id = " + id).executeUpdate();
            session.getTransaction().commit();
            System.out.println(ANSI_YELLOW + "\t\t*** ТРАНЗАКЦИЯ ЗАВЕРШЕНА ***" + RESET);

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                System.out.println(ANSI_YELLOW + "ТРАНЗАКЦИЯ ОТМЕНЕНА " +
                        ANSI_BLUE + "Произошла ошибка во время операции." + RESET);
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        Transaction transaction = null;
        try {
            Session session = FACTORY.getCurrentSession();
            transaction = session.beginTransaction();
            userList = session.createQuery("from User").getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                System.out.println(ANSI_YELLOW + "ТРАНЗАКЦИЯ ОТМЕНЕНА " +
                        ANSI_BLUE + "Произошла ошибка во время операции." + RESET);
            }
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try{
            Session session = FACTORY.getCurrentSession();
            transaction = session.beginTransaction();
            session.createQuery("delete User").executeUpdate();
            session.getTransaction().commit();
            System.out.println(ANSI_BLUE + "\t\t*** ОЧИСТКА ЗАВЕРШЕНА ***" + RESET);

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                System.out.println(ANSI_YELLOW + "ТРАНЗАКЦИЯ ОТМЕНЕНА " +
                        ANSI_BLUE + "Произошла ошибка во время операции." + RESET);
            }
        }
    }


    public void closeFactory() {
        System.out.println("Закрываю Session factory");
        FACTORY.close();
    }
}
