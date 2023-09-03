package by.clevertec.test.lobacevich.bank.dao.impl;

import by.clevertec.test.lobacevich.bank.dao.BankDao;
import by.clevertec.test.lobacevich.bank.di.Singleton;
import by.clevertec.test.lobacevich.bank.entity.Bank;
import by.clevertec.test.lobacevich.bank.exception.DataBaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class BankDaoImpl extends AbstractDao<Bank> implements BankDao {

    private static final String CREATE_BANK = "INSERT INTO banks(name, address) VALUES(?, ?);";
    private static final String UPDATE_BANK = "UPDATE banks SET name=?, address=? WHERE id=?;";
    private static final String DELETE_BANK = "DELETE FROM banks WHERE id=?;";
    private static final String GET_BY_ID = "SELECT * FROM banks WHERE id=?";
    private static final String GET_ALL = "SELECT * FROM banks";
    private static final String GET_BY_NAME = "SELECT * FROM banks WHERE name=?";

    @Override
    public void createEntity(Bank bank, Connection connection) throws DataBaseException {
        try (PreparedStatement ps = connection.prepareStatement(CREATE_BANK)){
            ps.setString(1, bank.getName());
            ps.setString(2, bank.getAddress());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException("DB failed: Can't create bank");
        }
    }

    @Override
    public void updateEntity(Bank bank, Connection connection) throws DataBaseException {
        try (PreparedStatement ps = connection.prepareStatement(UPDATE_BANK)) {
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

    @Override
    public List<Bank> getAllEntities(Connection connection) throws DataBaseException {
        List<Bank> banks = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(GET_ALL)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Bank bank = resultSetToBank(rs);
                banks.add(bank);
            }
            return banks;
        } catch (SQLException e) {
            throw new DataBaseException("DB failed: Can't load banks");
        }
    }

    @Override
    public Bank getBankByName(String name, Connection connection) throws DataBaseException {
        try (PreparedStatement ps = connection.prepareStatement(GET_BY_NAME)) {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return resultSetToBank(rs);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DataBaseException("DB failed: Can't get bank by name");
        }
    }
}
