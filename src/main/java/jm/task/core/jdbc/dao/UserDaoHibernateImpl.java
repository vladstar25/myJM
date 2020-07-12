package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Entity;
import javax.persistence.Query;
import java.sql.ResultSet;
import java.util.List;

@Entity
public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Session session = Util.getSession()) {
            session.getTransaction().begin();
            session.createSQLQuery("CREATE TABLE users (id INT PRIMARY KEY AUTO_INCREMENT, firstname VARCHAR (50), lastname VARCHAR(50), age INT )");
            session.flush();
            session.getTransaction().commit();
            System.out.println("Таблица создана....");

        } catch (Throwable ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSession()) {
            session.getTransaction().begin();
            session.createSQLQuery("DROP TABLE users");
            session.getTransaction().commit();
            System.out.println("Таблица удалена...");
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSession()) {
            session.getTransaction().begin();
            String querty = "INSERT INTO users (firstname, lastname, age) VALUES (\"" + name + "\", \"" + lastName + "\", " + (int) age + ")";
            session.createSQLQuery(querty);
            session.getTransaction().commit();
            System.out.println("User = " + name + " добавлен в базу");
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSession()) {
            session.getTransaction().begin();
            session.createSQLQuery("DELETE FROM users WHERE id = " + id);
            session.getTransaction().commit();
            System.out.println("User с id = " + id + " удален.");
        } catch (Throwable ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = null;
        try (Session session = Util.getSession()) {
            session.beginTransaction();
            list = session.createSQLQuery("SELECT firstname, lastname, age FROM users").list();
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSession()) {
            session.getTransaction().begin();
            session.createSQLQuery("TRUNCATE TABLE users");
            session.getTransaction().commit();
            System.out.println("Таблица очищена...");
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }
}
