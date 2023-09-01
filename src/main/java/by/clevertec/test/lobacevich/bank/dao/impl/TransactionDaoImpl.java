package by.clevertec.test.lobacevich.bank.dao.impl;

import by.clevertec.test.lobacevich.bank.dao.TransactionDao;
import by.clevertec.test.lobacevich.bank.entity.Transaction;
import by.clevertec.test.lobacevich.bank.exception.DataBaseException;
import lombok.Cleanup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class TransactionDaoImpl extends AbstractDao<Transaction> implements TransactionDao {

    public static final String CREATE_TRANSACTION = "INSERT INTO transactions(account_sender_id, " +
            "account_receiver_id, date_time, summ VALUES(?, ?, ?, ?);";
    public static final String UPDATE_TRANSACTION = "UPDATE transactions SET account_sender_id=?, " +
            "account_receiver_id=?, date_time=?, summ=? WHERE id=?;";
    public static final String DELETE_TRANSACTION = "DELETE FROM transactions WHERE id=?;";
    public static final String GET_BY_ID = "SELECT * FROM transactions WHERE id=?";

    @Override
    public void createEntity(Transaction transaction, Connection connection) throws DataBaseException {
        try {
            getTransactionPreparedStatement(transaction, connection, CREATE_TRANSACTION).executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException("DB failed: Can't create transaction");
        }
    }

    private PreparedStatement getTransactionPreparedStatement(Transaction transaction, Connection connection, String sql)
            throws SQLException {
        @Cleanup
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setLong(1, transaction.getAccountSenderId());
        ps.setLong(2, transaction.getAccountReceiverId());
        ps.setObject(5, transaction.getDateTime());
        ps.setBigDecimal(6, transaction.getSum());
        return ps;
    }

    @Override
    public void updateEntity(Transaction transaction, Connection connection) throws DataBaseException {
        try {
            getTransactionPreparedStatement(transaction, connection, UPDATE_TRANSACTION).executeUpdate();
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
                return resultSetToTransaction(rs);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DataBaseException("DB failed: Can't get transaction by id");
        }
    }

    private Transaction resultSetToTransaction(ResultSet rs) {
        try {
            Transaction transaction = new Transaction(rs.getLong("id"));
            transaction.setAccountSenderId(rs.getLong("account_sender_id"));
            transaction.setAccountReceiverId(rs.getLong("account_receiver_id"));
            transaction.setDateTime((LocalDateTime) rs.getObject("date_time"));
            transaction.setSum(rs.getBigDecimal("summ"));
            return transaction;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
