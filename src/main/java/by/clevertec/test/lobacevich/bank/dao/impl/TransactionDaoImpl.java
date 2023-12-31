package by.clevertec.test.lobacevich.bank.dao.impl;

import by.clevertec.test.lobacevich.bank.dao.AccountDao;
import by.clevertec.test.lobacevich.bank.dao.TransactionDao;
import by.clevertec.test.lobacevich.bank.di.Dependency;
import by.clevertec.test.lobacevich.bank.di.Singleton;
import by.clevertec.test.lobacevich.bank.entity.Transaction;
import by.clevertec.test.lobacevich.bank.exception.DataBaseException;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * класс для записи объектов класса счет в банке в бд и формирования объектов на основании
 * ответов бд
 */
@Singleton
public class TransactionDaoImpl extends AbstractDao<Transaction> implements TransactionDao {

    @Dependency(implementation = "AccountDaoImpl")
    private AccountDao accountDao;
    private static final String CREATE_TRANSACTION = "INSERT INTO transactions(account_sender_id, " +
            "account_receiver_id, date_time, summ) VALUES(?, ?, ?, ?);";
    private static final String UPDATE_TRANSACTION = "UPDATE transactions SET account_sender_id=?, " +
            "account_receiver_id=?, date_time=?, summ=? WHERE id=?;";
    private static final String DELETE_TRANSACTION = "DELETE FROM transactions WHERE id=?;";
    private static final String GET_BY_ID = "SELECT * FROM transactions WHERE id=?";
    private static final String GET_ALL = "SELECT * FROM transactions ORDER BY id";

    /**
     * создает объект из записи в бд
     * @param transaction объект транзакция
     * @param connection соединение с бд
     * @throws DataBaseException в случае, если не удается связаться с бд пробрасывает в слой сервисов исключение
     */
    @Override
    public void createEntity(Transaction transaction, Connection connection) throws DataBaseException {
        try (PreparedStatement ps = connection.prepareStatement(CREATE_TRANSACTION)) {
            if (transaction.getAccountSender() != null) {
                ps.setLong(1, transaction.getAccountSender().getId());
            } else {
                ps.setObject(1, null);
            }
            if (transaction.getAccountReceiver() != null) {
                ps.setLong(2, transaction.getAccountReceiver().getId());
            } else {
                ps.setObject(2, null);
            }
            ps.setObject(3, transaction.getDateTime());
            ps.setBigDecimal(4, transaction.getSum());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException("DB failed: Can't create transaction");
        }
    }

    /**
     * обновляет запись в бд
     * @param transaction объект транзакция
     * @param connection соединение с бд
     * @throws DataBaseException в случае, если не удается связаться с бд пробрасывает в слой сервисов исключение
     */
    @Override
    public void updateEntity(Transaction transaction, Connection connection) throws DataBaseException {
        try (PreparedStatement ps = connection.prepareStatement(UPDATE_TRANSACTION)) {
            if (transaction.getAccountSender() != null) {
                ps.setLong(1, transaction.getAccountSender().getId());
            } else {
                ps.setObject(1, null);
            }
            if (transaction.getAccountReceiver() != null) {
                ps.setLong(2, transaction.getAccountReceiver().getId());
            } else {
                ps.setObject(2, null);
            }
            ps.setObject(3, transaction.getDateTime());
            ps.setBigDecimal(4, transaction.getSum());
            ps.setLong(5, transaction.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException("DB failed: Can't update transaction");
        }
    }

    /**
     * удаляет запись из бд
     * @param transaction объект транзакция
     * @param connection соединение с бд
     * @throws DataBaseException в случае, если не удается связаться с бд пробрасывает в слой сервисов исключение
     */
    @Override
    public void deleteEntity(Transaction transaction, Connection connection) throws DataBaseException {
        try (PreparedStatement ps = connection.prepareStatement(DELETE_TRANSACTION)) {
            ps.setLong(1, transaction.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException("DB failed: Can't delete transaction");
        }
    }

    /**
     * возвращает объект по номеру айди
     * @param id айди объекта
     * @param connection соединение с бд
     * @return объект транзакции
     * @throws DataBaseException в случае, если не удается связаться с бд пробрасывает в слой сервисов исключение
     */
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

    /**
     * создает объект на основании ответа из бд
     * @param rs ответ бд
     * @param connection соединение с бд
     * @return объект транзакции
     * @throws DataBaseException в случае, если не удается связаться с бд пробрасывает в слой сервисов исключение
     */
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

    @Override
    public List<Transaction> getAllEntities(Connection connection) throws DataBaseException {
        List<Transaction> transactions = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(GET_ALL)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Transaction transaction = resultSetToTransaction(rs, connection);
                transactions.add(transaction);
            }
            return transactions;
        } catch (SQLException e) {
            throw new DataBaseException("DB failed: Can't load accounts");
        }
    }
}
