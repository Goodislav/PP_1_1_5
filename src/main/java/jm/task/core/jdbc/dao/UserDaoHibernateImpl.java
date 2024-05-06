package jm.task.core.jdbc.dao;

import com.mysql.cj.Query;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static jm.task.core.jdbc.util.Util.closeSessionFactory;

public class UserDaoHibernateImpl implements UserDao {
    private static final SessionFactory sessionFactory = Util.getSessionFactory();
    private static final Logger logger = Logger.getLogger(UserDaoJDBCImpl.class.getName());
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            try {
                Transaction transaction = session.beginTransaction();
                session.createSQLQuery("CREATE TABLE IF NOT EXISTS Users(" +
                        "id bigint," +
                        "name varchar(255)," +
                        "lastname varchar(255)," +
                        "age tinyint" +
                        ")").executeUpdate();
                transaction.commit();
                closeSessionFactory();
            } catch (HibernateException he) {
                logger.log(Level.SEVERE, "Ошибка создания таблицы \n", he);
                if (session.getTransaction().isActive()) {
                    session.getTransaction().rollback();
                }
            }
        }
    }

    @Override
    public void dropUsersTable() {

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

    }

    @Override
    public void removeUserById(long id) {

    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public void cleanUsersTable() {

    }
}
