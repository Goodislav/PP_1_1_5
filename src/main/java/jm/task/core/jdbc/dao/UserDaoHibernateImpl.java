package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoHibernateImpl implements UserDao {
    private static final SessionFactory sessionFactory = Util.Hibernate.getSessionFactory();
    private static final Logger logger = Logger.getLogger(UserDaoHibernateImpl.class.getName());

    public UserDaoHibernateImpl() {

    }

    //    Создание таблицы для User(ов)
    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            try {
                Transaction transaction = session.beginTransaction();
                session.createSQLQuery("CREATE TABLE IF NOT EXISTS users(" +
                        "id bigint," +
                        "name varchar(255)," +
                        "lastname varchar(255)," +
                        "age tinyint" +
                        ")").executeUpdate();
                transaction.commit();
            } catch (HibernateException he) {
                logger.log(Level.SEVERE, "Ошибка создания таблицы \n", he);
                if (session.getTransaction().isActive()) {
                    session.getTransaction().rollback();
                }
            }
        }
    }

    //    Удаление таблицы User(ов)
    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            try {
                Transaction transaction = session.beginTransaction();
                session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
                transaction.commit();
            } catch (HibernateException he) {
                logger.log(Level.SEVERE, "Ошибка удаления таблицы \n", he);
                if (session.getTransaction().isActive()) {
                    session.getTransaction().rollback();
                }
            }
        }
    }

    //    Добавление User(а) в таблицу
    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.openSession()) {
            try {
                Transaction transaction = session.beginTransaction();
                User user = new User(name, lastName, age);
                session.save(user);
                transaction.commit();
            } catch (HibernateException he) {
                String message = String.format("User с именем — %s не добавлен в базу данных \n", name);
                logger.log(Level.SEVERE, message, he);
                if (session.getTransaction().isActive()) {
                    session.getTransaction().rollback();
                }
            }
        }
    }

    //    Удаление User(а) из таблицы (по id)
    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            try {
                Transaction transaction = session.beginTransaction();
                User user = session.get(User.class, id);
                session.remove(user);
                transaction.commit();
            } catch (HibernateException he) {
                String message = String.format("Ошибка удаления user-a с id - %d \n", id);
                logger.log(Level.SEVERE, message, he);
                if (session.getTransaction().isActive()) {
                    session.getTransaction().rollback();
                }
            }
        }
    }

    //    Получение всех User(ов) из таблицы
    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            try {
                Transaction transaction = session.beginTransaction();
                users = session.createQuery("FROM User", User.class).list();
                transaction.commit();
            } catch (HibernateException he) {
                logger.log(Level.SEVERE, "Неудачная попытка получить всех user-ов \n", he);
                if (session.getTransaction().isActive()) {
                    session.getTransaction().rollback();
                }
            }
        }
        return users;
    }

    //    Очистка содержания таблицы
    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            try {
                Transaction transaction = session.beginTransaction();
                session.createQuery("DELETE FROM User").executeUpdate();
                transaction.commit();
            } catch (HibernateException he) {
                logger.log(Level.SEVERE, "Возникла ошибка при попытке очистить таблицу \n", he);
                if (session.getTransaction().isActive()) {
                    session.getTransaction().rollback();
                }
            }
        }
    }
}
