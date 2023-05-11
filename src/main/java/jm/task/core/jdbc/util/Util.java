package jm.task.core.jdbc.util;


import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/pp_base?useSSL=false";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "root";
    private static final String DB_DIALECT = "org.hibernate.dialect.MySQL5Dialect";

    //----------------------------------Hibernate connect----------------------------------------
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();//Для настройки параметров

                Properties settingHibernate = new Properties();
                settingHibernate.put(Environment.DRIVER, DB_DRIVER);
                settingHibernate.put(Environment.URL, DB_URL);
                settingHibernate.put(Environment.USER, DB_USERNAME);
                settingHibernate.put(Environment.PASS, DB_PASSWORD);
                settingHibernate.put(Environment.DIALECT, DB_DIALECT);

                settingHibernate.put(Environment.SHOW_SQL, "true");
                settingHibernate.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                settingHibernate.put(Environment.HBM2DDL_AUTO, "");

                configuration.setProperties(settingHibernate);
                configuration.addAnnotatedClass(User.class); // Указываем класс для сопоставления с таблицей

                //Создание объекта для управления службами
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                //Сюда упаковываем все результаты
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
                System.out.println("Подключились к БД через Hibernate");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Ошибка подключения к БД через Hibernate");
            }
        }
        return sessionFactory;
    }
}