package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserService userDaoHibernate = new UserServiceImpl();


        userDaoHibernate.createUsersTable();

        userDaoHibernate.saveUser("Anton", "Shorin", (byte) 31);
        userDaoHibernate.saveUser("Olga", "Shorina", (byte) 31);
        userDaoHibernate.saveUser("Kirill", "Shorin", (byte) 1);
        userDaoHibernate.saveUser("Koshka", "Domashnya", (byte) 8);

        userDaoHibernate.getAllUsers();
        userDaoHibernate.cleanUsersTable();
        userDaoHibernate.dropUsersTable();
    }
}