package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.*;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private final String CREATE_TABLE = "CREATE TABLE iF NOT EXISTS new_table3 (\n" +
            "  `id` BIGINT NOT NULL AUTO_INCREMENT,\n" +
            "  `name` VARCHAR(45) NOT NULL,\n" +
            "  `lastName` VARCHAR(45) NOT NULL,\n" +
            "  `age` TINYINT NOT NULL,\n" +
            "  PRIMARY KEY (`id`));";

    private final String DROP_TABLE = "DROP TABLE iF EXISTS new_table3;";
    private final String TRUNCATE = "TRUNCATE TABLE new_table3;";
    private final String GET_ALL = "SELECT * FROM new_table3;";

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery(CREATE_TABLE).addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            System.err.println("Ошибка при создании таблицы");
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery(DROP_TABLE).addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            System.err.println("Ошибка при удалении таблицы");
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = null;
        User user = new User(name, lastName, age);
        try {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (HibernateException e) {
            System.err.println("Ошибка при добавлении пользоватля");
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = null;
        User user;
        try {
            transaction = session.beginTransaction();
            user = (User) session.get(User.class, id);
            session.delete(user);
            transaction.commit();
        } catch (HibernateException e) {
            System.err.println("Ошибка при удалении пользователя");
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    @Override
    public List<User> getAllUsers() {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = null;
        List<User> users = null;
        try {
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery(GET_ALL).addEntity(User.class);
            users = query.list();
            //users = session.createCriteria(User.class).list();
            transaction.commit();
        } catch (Exception e) {
            System.err.println("Ошибка при получении списка из таблицы");
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery(TRUNCATE).addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            System.err.println("Ошибка при очистке таблицы");
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
