package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Supplier;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try {
            Util.conDB().execute("CREATE TABLE users (id INT PRIMARY KEY AUTO_INCREMENT, firstname VARCHAR (50), lastname VARCHAR(50), age INT )");
            System.out.println("Таблица создана");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void dropUsersTable() throws SQLException {
        ResultSet resultSet = Util.conDB().executeQuery("SHOW TABLES");
        if (resultSet.next()) {
            String str = resultSet.getString("Tables_in_testDB");
            if (str.contains("users")) {
                Util.conDB().execute("DROP TABLE users");
                System.out.println("Таблица удалена");
            }
        } else {
            System.out.println("Таблицы не существует");
        }

    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        String querty = "INSERT INTO users (firstname, lastname, age) VALUES (\"" + name + "\", \"" + lastName + "\", " + (int) age + ")";
        Util.conDB().execute(querty);
        System.out.println("User с именем " + name + " добавлен в базу.");
    }

    public void removeUserById(long id) throws SQLException {
        Util.conDB().execute("DELETE FROM users WHERE id = " + id);
    }

    public List<User> getAllUsers() throws SQLException {
        ResultSet rS = Util.conDB().executeQuery("SELECT firstname, lastname, age FROM users");
        List<User> listUsers = new ArrayList<User>();
        Supplier<User> supplier = () -> {
            String firstname = null;
            String lastname = null;
            byte age = 0;
            try {
                firstname = rS.getString("firstname");
                lastname = rS.getString("lastname");
                age = (byte) rS.getInt("age");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return new User(firstname, lastname, age);
        };
        while (rS.next()) {
            listUsers.add(supplier.get());
        }

        return listUsers;
    }

    public void cleanUsersTable() throws SQLException {
        Util.conDB().execute("TRUNCATE TABLE users");
    }
}
