package by.clevertec.test.lobacevich.bank.dao.impl;

import by.clevertec.test.lobacevich.bank.dao.BankDao;
import by.clevertec.test.lobacevich.bank.entity.Bank;
import by.clevertec.test.lobacevich.bank.exception.DataBaseException;
import lombok.Cleanup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BankDaoImpl extends AbstractDao<Bank> implements BankDao {

    public static final String CREATE_BANK = "INSERT INTO banks(name, address) VALUES(?, ?);";
    public static final String UPDATE_BANK = "UPDATE banks SET name=?, address=? WHERE id=?;";
    public static final String DELETE_BANK = "DELETE FROM banks WHERE id=?;";
    public static final String GET_BY_ID = "SELECT * FROM banks WHERE id=?";

    @Override
    public void createEntity(Bank bank, Connection connection) throws DataBaseException {
        try {
            getBankPreparedStatement(bank, connection, CREATE_BANK).executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException("DB failed: Can't create bank");
        }
    }

    private PreparedStatement getBankPreparedStatement(Bank bank, Connection connection, String sql)
            throws SQLException {
        @Cleanup
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, bank.getName());
        ps.setString(2, bank.getAddress());
        return ps;
    }

    @Override
    public void updateEntity(Bank bank, Connection connection) throws DataBaseException {
        try {
            getBankPreparedStatement(bank, connection, UPDATE_BANK).executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException("DB failed: Can't update bank");
        }
    }

    @Override
    public void deleteEntity(Bank bank, Connection connection) throws DataBaseException {
        try (PreparedStatement ps = connection.prepareStatement(DELETE_BANK)) {
            ps.setLong(1, bank.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException("DB failed: Can't delete bank");
        }
    }

    @Override
    public Bank getEntityById(long id, Connection connection) throws DataBaseException {
        try (PreparedStatement ps = connection.prepareStatement(GET_BY_ID)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return resultSetToBank(rs);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DataBaseException("DB failed: Can't get bank by id");
        }
    }

    private Bank resultSetToBank(ResultSet rs) {
        try {
            Bank bank = new Bank(rs.getLong("id"));
            bank.setName(rs.getString("name"));
            bank.setAddress(rs.getString("address"));
            return bank;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
