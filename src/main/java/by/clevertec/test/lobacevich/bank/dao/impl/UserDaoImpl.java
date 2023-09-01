package by.clevertec.test.lobacevich.bank.dao.impl;

import by.clevertec.test.lobacevich.bank.dao.UserDao;
import by.clevertec.test.lobacevich.bank.entity.User;
import by.clevertec.test.lobacevich.bank.exception.DataBaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl extends AbstractDao<User> implements UserDao {

    public static UserDaoImpl INSTANCE = new UserDaoImpl();

    public static final String CREATE_USER = "INSERT INTO users(firstname, lastname, personal_number, address) " +
            "VALUES(?, ?, ?, ?);";
    public static final String UPDATE_USER = "UPDATE users SET firstname=?, lastname=?, personal_number=?, " +
            "address=? WHERE id=?;";
    public static final String DELETE_USER = "DELETE FROM users WHERE id=?;";
    public static final String GET_BY_ID = "SELECT * FROM users WHERE id=?";

    private UserDaoImpl() {
    }

    public static UserDaoImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public void createEntity(User user, Connection connection) throws DataBaseException {
        try (PreparedStatement ps = connection.prepareStatement(CREATE_USER)) {
            ps.setString(1, user.getFirstname());
            ps.setString(2, user.getLastname());
            ps.setString(3, user.getPassportPersonalNumber());
            ps.setString(4, user.getAddress());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException("DB failed: Can't create user");
        }
    }

    @Override
    public void updateEntity(User user, Connection connection) throws DataBaseException {
        try (PreparedStatement ps = connection.prepareStatement(UPDATE_USER)) {
            ps.setString(1, user.getFirstname());
            ps.setString(2, user.getLastname());
            ps.setString(3, user.getPassportPersonalNumber());
            ps.setString(4, user.getAddress());
            ps.setLong(5, user.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException("DB failed: Can't update user");
        }
    }

    @Override
    public void deleteEntity(User user, Connection connection) throws DataBaseException {
        try (PreparedStatement ps = connection.prepareStatement(DELETE_USER)) {
            ps.setLong(1, user.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException("DB failed: Can't delete user");
        }
    }

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

    private User resultSetToUser(ResultSet rs) throws DataBaseException {
        try {
            User user = new User(rs.getLong("id"));
            user.setFirstname(rs.getString("firstname"));
            user.setLastname(rs.getString("lastname"));
            user.setPassportPersonalNumber(rs.getString("personal_number"));
            user.setAddress(rs.getString("address"));
            return user;
        } catch (SQLException e) {
            throw new DataBaseException("DB failed: Can't load user");
        }
    }
}