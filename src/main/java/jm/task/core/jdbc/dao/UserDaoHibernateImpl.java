package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    private static final String sqlCreateHibernate = """
            CREATE TABLE IF NOT EXISTS pp_base.users(
            id SERIAL NOT NULL,
            name VARCHAR(25),
            lastName VARCHAR(45),
            age TINYINT(3) NOT NULL CHECK (age >= 0 AND age <= 127),
            PRIMARY KEY (id)
            )""";
    private static final String sqlDropHibernate = "DROP TABLE IF EXISTS users";
    private static final String saveUserSQLHibernate = """
            INSERT INTO users(name, lastName, age)
            VALUES (?, ?, ?)""";
    private static final String getUsersSQLHibernate = "FROM User";
    private static final String removeUserSQLHibernate = "DELETE FROM users WHERE id = ?";
    private static final String clearTableHibernate = "TRUNCATE users";

    private static final SessionFactory sessionFactory = Util.getSessionFactory();

    @Override
    public void createUsersTable() {
        Session session = sessionFactory.openSession();
        try (session) {
            session.beginTransaction();
            session.createSQLQuery(sqlCreateHibernate)
                    .addEntity(User.class).executeUpdate();
            session.getTransaction().commit();
            System.out.println("Создали таблицу");
        } catch (Exception e) {
            if (session != null) session.getTransaction().rollback();
            e.printStackTrace();
            System.out.println("Не получилось создать таблицу");
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = sessionFactory.openSession();
        try (session) {
            session.beginTransaction();
            session.createSQLQuery(sqlDropHibernate)
                    .addEntity(User.class).executeUpdate();
            session.getTransaction().commit();
            System.out.println("Удалили таблицу");
        } catch (Exception e) {
            if (session != null) session.getTransaction().rollback();
            e.printStackTrace();
            System.out.println("Не получилось удалить таблицу");
        }
    }

    @Override

    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        Session session = sessionFactory.openSession();
        try (session) {
            User user = new User(name, lastName, age);
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            System.out.println("Юзер с именем " + name + " добавлен в базу данных");
        } catch (Exception e) {
            if (session != null) session.getTransaction().rollback();
            e.printStackTrace();
            System.out.println("Не получилось добавить пользователей");
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        Session session = sessionFactory.openSession();
        try (session) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            transaction.commit();
        } catch (Exception e) {
            if (session != null) session.getTransaction().rollback();
            e.printStackTrace();
            System.out.println("Не получилось удалить пользователя");
        }
    }

    @Override
    public List<User> getAllUsers() {
        Transaction transaction = null;
        Session session = sessionFactory.openSession();
        List<User> users = null;
        try (session) {
            transaction = session.beginTransaction();
            users = session.createQuery(getUsersSQLHibernate).getResultList();
            for (User result : users) {
                System.out.println(result);
            }
            transaction.commit();
        } catch (Exception e) {
            if (session != null) session.getTransaction().rollback();
            e.printStackTrace();
            System.out.println("Не получилось прочитать всех пользователей");
        }
        return users;
    }
    
    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        Session session = sessionFactory.openSession();
        try (session) {
            transaction = session.beginTransaction();
            session.createSQLQuery(clearTableHibernate).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (session != null) session.getTransaction().rollback();
            e.printStackTrace();
            System.out.println("Не получилось очистить таблицу");
        }
    }
}