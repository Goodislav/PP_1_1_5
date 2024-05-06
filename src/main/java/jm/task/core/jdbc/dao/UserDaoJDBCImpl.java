package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoJDBCImpl implements UserDao {
    private Connection connection = Util.getConnection();
    private static final Logger logger = Logger.getLogger(UserDaoJDBCImpl.class.getName());

    public UserDaoJDBCImpl() {

    }

//    Создание таблицы для User(ов)
    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS USERS(" +
                "ID BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                "NAME VARCHAR(255), " +
                "LASTNAME VARCHAR(255), " +
                "AGE TINYINT)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.execute();
            logger.info("Таблица пользователей успешно создана \n");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка создания таблицы \n", e);
        }
    }

//    Удаление таблицы User(ов)
    public void dropUsersTable() {
        String sql = " DROP TABLE IF EXISTS USERS";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка удаления таблицы \n", e);
        }
    }

//    Добавление User(а) в таблицу
    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO USERS (NAME, LASTNAME, AGE) VALUES (?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setByte(3, age);

            ps.executeUpdate();
        } catch (SQLException e) {
            String message = String.format("User с именем — %s не добавлен в базу данных \n", name);
            logger.log(Level.SEVERE, message, e);
        }
    }

//    Удаление User(а) из таблицы (по id)
    public void removeUserById(long id) {
        String sql = "DELETE FROM USERS WHERE ID = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);

            ps.executeUpdate();
        } catch (SQLException e) {
            String message = String.format("Ошибка удаления user-a с id - %d \n", id);
            logger.log(Level.SEVERE, message, e);
        }
    }

//    Получение всех User(ов) из таблицы
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        String sql = "SELECT * FROM USERS";

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet resultSet = ps.executeQuery()) {

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("ID"));
                user.setName(resultSet.getString("NAME"));
                user.setLastName(resultSet.getString("LASTNAME"));
                user.setAge(resultSet.getByte("AGE"));

                users.add(user);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE,"Неудачная попытка получить всех user-ов \n", e);
        }
        return users;
    }

//    Очистка содержания таблицы

    public void cleanUsersTable() {
        String sql = "DELETE FROM USERS";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Возникла ошибка при попытке очистить таблицу \n", e);
        }
    }
}
