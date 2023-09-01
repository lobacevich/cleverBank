package by.clevertec.test.lobacevich.bank.dao.impl;

import by.clevertec.test.lobacevich.bank.dao.AccountDao;
import by.clevertec.test.lobacevich.bank.dao.TransactionDao;
import by.clevertec.test.lobacevich.bank.entity.Transaction;
import by.clevertec.test.lobacevich.bank.exception.DataBaseException;

import java.sql.*;
import java.time.LocalDateTime;

public class TransactionDaoImpl extends AbstractDao<Transaction> implements TransactionDao {

    private final AccountDao accountDao = AccountDaoImpl.getInstance();
    private static final TransactionDaoImpl INSTANCE = new TransactionDaoImpl();
    public static final String CREATE_TRANSACTION = "INSERT INTO transactions(account_sender_id, " +
            "account_receiver_id, date_time, summ) VALUES(?, ?, ?, ?);";
    public static final String UPDATE_TRANSACTION = "UPDATE transactions SET account_sender_id=?, " +
            "account_receiver_id=?, date_time=?, summ=? WHERE id=?;";
    public static final String DELETE_TRANSACTION = "DELETE FROM transactions WHERE id=?;";
    public static final String GET_BY_ID = "SELECT * FROM transactions WHERE id=?";

    private TransactionDaoImpl() {
    }

    public static TransactionDaoImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public void createEntity(Transaction transaction, Connection connection) throws DataBaseException {
        try (PreparedStatement ps = connection.prepareStatement(CREATE_TRANSACTION)) {
            if (transaction.getAccountSender() != null) {
                ps.setLong(1, transaction.getAccountSender().getId());
            } else {
                ps.setLong(1, -1);
            }
            if (transaction.getAccountReceiver() != null) {
                ps.setLong(2, transaction.getAccountReceiver().getId());
            } else {
                ps.setLong(2, -1);
            }
            ps.setLong(2, transaction.getAccountReceiver().getId());
            ps.setObject(3, transaction.getDateTime());
            ps.setBigDecimal(4, transaction.getSum());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException("DB failed: Can't create transaction");
        }
    }

    @Override
    public void updateEntity(Transaction transaction, Connection connection) throws DataBaseException {
        try (PreparedStatement ps = connection.prepareStatement(UPDATE_TRANSACTION)) {
            if (transaction.getAccountSender() != null) {
                ps.setLong(1, transaction.getAccountSender().getId());
            } else {
                ps.setLong(1, -1);
            }
            if (transaction.getAccountReceiver() != null) {
                ps.setLong(2, transaction.getAccountReceiver().getId());
            } else {
                ps.setLong(2, -1);
            }
            ps.setObject(3, transaction.getDateTime());
            ps.setBigDecimal(4, transaction.getSum());
            ps.setLong(5, transaction.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException("DB failed: Can't update transaction");
        }
    }

    @Override
    public void deleteEntity(Transaction transaction, Connection connection) throws DataBaseException {
        try (PreparedStatement ps = connection.prepareStatement(DELETE_TRANSACTION)) {
            ps.setLong(1, transaction.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException("DB failed: Can't delete transaction");
        }
    }

    @Override
    public Transaction getEntityById(long id, Connection connection) throws DataBaseException {
        try (PreparedStatement ps = connection.prepareStatement(GET_BY_ID)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return resultSetToTransaction(rs, connection);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DataBaseException("DB failed: Can't get transaction by id");
        }
    }

    private Transaction resultSetToTransaction(ResultSet rs, Connection connection) throws DataBaseException {
        try {
            Transaction transaction = new Transaction(rs.getLong("id"));
            transaction.setAccountSender(accountDao.getEntityById(rs.getLong("account_sender_id"),
                    connection));
            transaction.setAccountReceiver(accountDao.getEntityById(rs.getLong("account_receiver_id"),
                    connection));
            transaction.setDateTime(rs.getObject("date_time", LocalDateTime.class));
            transaction.setSum(rs.getBigDecimal("summ"));
            return transaction;
        } catch (SQLException e) {
            throw new DataBaseException("DB failed: Can't load transaction");
        }
    }
}
