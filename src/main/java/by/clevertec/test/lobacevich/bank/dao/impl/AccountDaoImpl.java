package by.clevertec.test.lobacevich.bank.dao.impl;

import by.clevertec.test.lobacevich.bank.dao.AccountDao;
import by.clevertec.test.lobacevich.bank.dao.BankDao;
import by.clevertec.test.lobacevich.bank.dao.UserDao;
import by.clevertec.test.lobacevich.bank.entity.Account;
import by.clevertec.test.lobacevich.bank.exception.DataBaseException;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class AccountDaoImpl extends AbstractDao<Account> implements AccountDao {

    private final UserDao userDao = UserDaoImpl.getInstance();
    private final BankDao bankDao = BankDaoImpl.getInstance();
    public static AccountDaoImpl INSTANCE = new AccountDaoImpl();
    public static final String CREATE_ACCOUNT = "INSERT INTO accounts(user_id, bank_id, account_number, " +
            "creation_date, balance) VALUES(?, ?, ?, ?, ?);";
    public static final String UPDATE_ACCOUNT = "UPDATE accounts SET user_id=?, bank_id=?, " +
            "account_number=?, creation_date=?, balance=? WHERE id=?;";
    public static final String DELETE_ACCOUNT = "DELETE FROM accounts WHERE id=?;";
    public static final String GET_BY_ID = "SELECT * FROM accounts WHERE id=?";

    private AccountDaoImpl() {
    }

    public static AccountDaoImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public void createEntity(Account account, Connection connection) throws DataBaseException {
        try (PreparedStatement ps = connection.prepareStatement(CREATE_ACCOUNT)) {
            ps.setLong(1, account.getUser().getId());
            ps.setLong(2, account.getBank().getId());
            ps.setInt(3, account.getAccountNumber());
            ps.setDate(4, Date.valueOf(account.getCreationDate()));
            ps.setBigDecimal(5, account.getBalance());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException("DB failed: Can't create account");
        }
    }

    @Override
    public void updateEntity(Account account, Connection connection) throws DataBaseException {
        try (PreparedStatement ps = connection.prepareStatement(UPDATE_ACCOUNT)) {
            ps.setLong(1, account.getUser().getId());
            ps.setLong(2, account.getBank().getId());
            ps.setInt(3, account.getAccountNumber());
            ps.setDate(4, Date.valueOf(account.getCreationDate()));
            ps.setBigDecimal(5, account.getBalance());
            ps.setLong(6, account.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException("DB failed: Can't update account");
        }
    }

    @Override
    public void deleteEntity(Account account, Connection connection) throws DataBaseException {
        try (PreparedStatement ps = connection.prepareStatement(DELETE_ACCOUNT)) {
            ps.setLong(1, account.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException("DB failed: Can't delete account");
        }
    }

    @Override
    public Account getEntityById(long id, Connection connection) throws DataBaseException {
        try (PreparedStatement ps = connection.prepareStatement(GET_BY_ID)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return resultSetToAccount(rs, connection);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DataBaseException("DB failed: Can't get account by id");
        }
    }

    private Account resultSetToAccount(ResultSet rs, Connection connection) throws DataBaseException {
        try {
            Account account = new Account(rs.getLong("id"));
            account.setUser(userDao.getEntityById(rs.getLong("user_id"), connection));
            account.setBank(bankDao.getEntityById(rs.getLong("bank_id"), connection));
            account.setAccountNumber(rs.getInt("account_number"));
            account.setCreationDate(rs.getDate("creation_date").toLocalDate());
            account.setBalance(rs.getBigDecimal("balance"));
            return account;
        } catch (SQLException e) {
            throw new DataBaseException("DB failed: Can't load account");
        }
    }
}
