package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;

import java.util.Arrays;


public class Main {
    public static void main(String[] args) {
        UserDaoHibernateImpl impl = new UserDaoHibernateImpl();
        impl.createUsersTable();
        impl.saveUser("Oleg", "Boyko", (byte) 25);
        impl.saveUser("Ivan", "Umudov", (byte) 15);
        impl.saveUser("Nikolai", "Vasilev", (byte) 45);
        impl.saveUser("Marina", "Anoshina", (byte) 76);
        impl.removeUserById(1);
        impl.getAllUsers().stream()
                .forEach(System.out::println);
        impl.cleanUsersTable();

        impl.getAllUsers().stream()
                .forEach(System.out::println);

        impl.dropUsersTable();

        impl.closeFactory();

    }
}
