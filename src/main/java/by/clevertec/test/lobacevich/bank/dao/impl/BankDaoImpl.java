package by.clevertec.test.lobacevich.bank.dao.impl;

import by.clevertec.test.lobacevich.bank.dao.BankDao;
import by.clevertec.test.lobacevich.bank.entity.Bank;
import by.clevertec.test.lobacevich.bank.exception.DataBaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BankDaoImpl extends AbstractDao<Bank> implements BankDao {

    public static BankDaoImpl INSTANCE = new BankDaoImpl();

    public static final String CREATE_BANK = "INSERT INTO banks(name, address) VALUES(?, ?);";
    public static final String UPDATE_BANK = "UPDATE banks SET name=?, address=? WHERE id=?;";
    public static final String DELETE_BANK = "DELETE FROM banks WHERE id=?;";
    public static final String GET_BY_ID = "SELECT * FROM banks WHERE id=?";

    private BankDaoImpl() {
    }

    public static BankDaoImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public void createEntity(Bank bank, Connection connection) throws DataBaseException {
        try (PreparedStatement ps = connection.prepareStatement(CREATE_BANK);){
            ps.setString(1, bank.getName());
            ps.setString(2, bank.getAddress());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException("DB failed: Can't create bank");
        }
    }

    @Override
    public void updateEntity(Bank bank, Connection connection) throws DataBaseException {
        try (PreparedStatement ps = connection.prepareStatement(UPDATE_BANK);) {
            ps.setString(1, bank.getName());
            ps.setString(2, bank.getAddress());
            ps.setLong(3, bank.getId());
            ps.executeUpdate();
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

    private Bank resultSetToBank(ResultSet rs) throws DataBaseException {
        try {
            Bank bank = new Bank(rs.getLong("id"));
            bank.setName(rs.getString("name"));
            bank.setAddress(rs.getString("address"));
            return bank;
        } catch (SQLException e) {
            throw new DataBaseException("DB failed: Can't load bank");
        }
    }
}
