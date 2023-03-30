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
import java.util.logging.Logger;

public class UserDaoHibernateImpl implements UserDao {
    private static final Logger logger = Logger.getLogger(UserDaoHibernateImpl.class.getName());
    private static final String ANSI_YELLOW = "\u001B[1;33m";
    private static final String ANSI_RED = "\u001B[1;31m";

    private static final String ANSI_RESET = "\u001B[0m";
    private final SessionFactory factory = Util.createSession();
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try {
            Session session = factory.getCurrentSession();
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery("CREATE TABLE IF NOT EXISTS users (id INT primary key AUTO_INCREMENT," +
                    "name VARCHAR(60) NOT NULL," +
                    "last_name VARCHAR(60) NOT NULL," +
                    "age TINYINT NOT NULL)").addEntity(User.class);
            query.executeUpdate();
            session.getTransaction().commit();
            logger.info( ANSI_YELLOW + "CREATING THE TABLE SUCCESSFUL" + ANSI_RESET);
        } catch (Exception e) {
            if(transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try {
            Session session = factory.getCurrentSession();
            transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE if EXISTS users").executeUpdate();
            session.getTransaction().commit();
            logger.info(ANSI_RED + "THE TABLE DROPPED" + ANSI_RESET);
        } catch (Exception e) {
            if(transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try{
            Session session = factory.getCurrentSession();
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                e.printStackTrace();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try{
            Session session = factory.getCurrentSession();
            transaction = session.beginTransaction();
            session.createSQLQuery("delete from users where id = " + id).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        Transaction transaction = null;
        try {
            Session session = factory.getCurrentSession();
            transaction = session.beginTransaction();
            userList = session.createQuery("from User").getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try{
            Session session = factory.getCurrentSession();
            transaction = session.beginTransaction();
            session.createQuery("delete User").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }


    public void closeFactory() {
        factory.close();
    }
}
