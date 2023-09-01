package by.clevertec.test.lobacevich.bank.dao.impl;

import by.clevertec.test.lobacevich.bank.dao.AccountDao;
import by.clevertec.test.lobacevich.bank.entity.Account;
import by.clevertec.test.lobacevich.bank.entity.enums.Currency;
import by.clevertec.test.lobacevich.bank.exception.DataBaseException;
import lombok.Cleanup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class AccountDaoImpl extends AbstractDao<Account> implements AccountDao {

    public static final String CREATE_ACCOUNT = "INSERT INTO accounts(user_id, bank_id, currency, account_number, " +
            "creation_date, balance VALUES(?, ?, ?, ?, ?, ?);";
    public static final String UPDATE_ACCOUNT = "UPDATE accounts SET user_id=?, bank_id=?, currency=?, " +
            "account_number=?, creation_date=?, balance=? WHERE id=?;";
    public static final String DELETE_ACCOUNT = "DELETE FROM accounts WHERE id=?;";
    public static final String GET_BY_ID = "SELECT * FROM accounts WHERE id=?";

    @Override
    public void createEntity(Account account, Connection connection) throws DataBaseException {
        try {
            getAccountPreparedStatement(account, connection, CREATE_ACCOUNT).executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException("DB failed: Can't create account");
        }
    }

    private PreparedStatement getAccountPreparedStatement(Account account, Connection connection, String sql)
            throws SQLException {
        @Cleanup
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setLong(1, account.getUserId());
        ps.setLong(2, account.getBankId());
        ps.setString(3, account.getCurrency().toString());
        ps.setInt(4, account.getAccountNumber());
        ps.setObject(5, account.getCreationDate());
        ps.setBigDecimal(6, account.getBalance());
        return ps;
    }

    @Override
    public void updateEntity(Account account, Connection connection) throws DataBaseException {
        try {
            getAccountPreparedStatement(account, connection, UPDATE_ACCOUNT).executeUpdate();
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
                return resultSetToAccount(rs);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DataBaseException("DB failed: Can't get account by id");
        }
    }

    private Account resultSetToAccount(ResultSet rs) {
        try {
            Account account = new Account(rs.getLong("id"));
            account.setUserId(rs.getLong("user_id"));
            account.setBankId(rs.getLong("bank_id"));
            account.setCurrency(Currency.valueOf(rs.getString("currency")));
            account.setAccountNumber(rs.getInt("account_number"));
            account.setCreationDate((LocalDateTime) rs.getObject("creation_date"));
            account.setBalance(rs.getBigDecimal("balance"));
            return account;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
