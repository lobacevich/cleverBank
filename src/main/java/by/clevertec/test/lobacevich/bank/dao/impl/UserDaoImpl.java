package by.clevertec.test.lobacevich.bank.dao.impl;

import by.clevertec.test.lobacevich.bank.dao.UserDao;
import by.clevertec.test.lobacevich.bank.di.Singleton;
import by.clevertec.test.lobacevich.bank.entity.User;
import by.clevertec.test.lobacevich.bank.exception.DataBaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * класс для записи объектов класса счет в банке в бд и формирования объектов на основании
 * ответов бд
 */
@Singleton
public class UserDaoImpl extends AbstractDao<User> implements UserDao {

    private static final String CREATE_USER = "INSERT INTO users(firstname, lastname, surname, personal_number, address) " +
            "VALUES(?, ?, ?, ?, ?);";
    private static final String UPDATE_USER = "UPDATE users SET firstname=?, lastname=?, surname=?, personal_number=?, " +
            "address=? WHERE id=?;";
    private static final String DELETE_USER = "DELETE FROM users WHERE id=?;";
    private static final String GET_BY_ID = "SELECT * FROM users WHERE id=?";

    /**
     * создает запись об объекте в бд
     * @param user
     * @param connection соединение с бд
     * @throws DataBaseException в случае, если не удается связаться с бд пробрасывает в слой сервисов исключение
     */
    @Override
    public void createEntity(User user, Connection connection) throws DataBaseException {
        try (PreparedStatement ps = connection.prepareStatement(CREATE_USER)) {
            ps.setString(1, user.getFirstname());
            ps.setString(2, user.getLastname());
            ps.setString(3, user.getSurname());
            ps.setString(4, user.getPassportPersonalNumber());
            ps.setString(5, user.getAddress());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException("DB failed: Can't create user");
        }
    }

    /**
     * обновляет запись об объекте в бд
     * @param user объект пользователь
     * @param connection соединение с бд
     * @throws DataBaseException в случае, если не удается связаться с бд пробрасывает в слой сервисов исключение
     */
    @Override
    public void updateEntity(User user, Connection connection) throws DataBaseException {
        try (PreparedStatement ps = connection.prepareStatement(UPDATE_USER)) {
            ps.setString(1, user.getFirstname());
            ps.setString(2, user.getLastname());
            ps.setString(3, user.getSurname());
            ps.setString(4, user.getPassportPersonalNumber());
            ps.setString(5, user.getAddress());
            ps.setLong(6, user.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException("DB failed: Can't update user");
        }
    }

    /**
     * удаляет запись об объекте из бд
     * @param user объект пользователь
     * @param connection соединение с бд
     * @throws DataBaseException в случае, если не удается связаться с бд пробрасывает в слой сервисов исключение
     */
    @Override
    public void deleteEntity(User user, Connection connection) throws DataBaseException {
        try (PreparedStatement ps = connection.prepareStatement(DELETE_USER)) {
            ps.setLong(1, user.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException("DB failed: Can't delete user");
        }
    }

    /**
     * возвращает объект по айди
     * @param id айди объекта
     * @param connection соединение с бд
     * @return объект пользователь
     * @throws DataBaseException в случае, если не удается связаться с бд пробрасывает в слой сервисов исключение
     */
    @Override
    public User getEntityById(long id, Connection connection) throws DataBaseException {
        try (PreparedStatement ps = connection.prepareStatement(GET_BY_ID)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return resultSetToUser(rs);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DataBaseException("DB failed: Can't get user by id");
        }
    }

    /**
     * возвращает объект по ответу от сервера
     * @param rs ответ от сервера
     * @return объект пользователь
     * @throws DataBaseException в случае, если не удается связаться с бд пробрасывает в слой сервисов исключение
     */
    private User resultSetToUser(ResultSet rs) throws DataBaseException {
        try {
            User user = new User(rs.getLong("id"));
            user.setFirstname(rs.getString("firstname"));
            user.setLastname(rs.getString("lastname"));
            user.setSurname(rs.getString("surname"));
            user.setPassportPersonalNumber(rs.getString("personal_number"));
            user.setAddress(rs.getString("address"));
            return user;
        } catch (SQLException e) {
            throw new DataBaseException("DB failed: Can't load user");
        }
    }
}