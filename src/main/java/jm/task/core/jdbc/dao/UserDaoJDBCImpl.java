package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private final String CREATE_TABLE = "CREATE TABLE iF NOT EXISTS new_table3 (\n" +
            "  `id` BIGINT NOT NULL AUTO_INCREMENT,\n" +
            "  `name` VARCHAR(45) NOT NULL,\n" +
            "  `lastName` VARCHAR(45) NOT NULL,\n" +
            "  `age` TINYINT NOT NULL,\n" +
            "  PRIMARY KEY (`id`));";

    private final String DROP_TABLE = "DROP TABLE iF EXISTS new_table3;";
    private final String INSERT_NEW = "INSERT INTO new_table3 (id, name, lastname, age) VALUES (DEFAULT, ?, ?, ?);";
    private final String DELETE_BY_ID = "DELETE FROM new_table3 WHERE id = ?;";
    private final String TRUNCATE = "TRUNCATE TABLE new_table3;";
    private final String GET_ALL = "SELECT * FROM new_table3;";

    private Connection getConnection() {
        Connection connection = null;
        try {
            connection = Util.getConnect();
        } catch (SQLException e) {
            System.err.println("Ошибка подключения к БД");
        }
        return connection;
    }

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(CREATE_TABLE)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Ошибка при создании таблицы");
        }
    }

    public void dropUsersTable() {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(DROP_TABLE)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении таблицы");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(INSERT_NEW)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            System.err.println("Ошибка при добавлении пользователя");
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(DELETE_BY_ID)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении пользователя");
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(GET_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении списка из таблицы");
        }
        return users;
    }

    public void cleanUsersTable() {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(TRUNCATE)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Ошибка при очистке содержания таблицы");
        }
    }
}
