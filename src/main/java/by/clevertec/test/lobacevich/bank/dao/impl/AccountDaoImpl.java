package by.clevertec.test.lobacevich.bank.dao.impl;

import by.clevertec.test.lobacevich.bank.dao.AccountDao;
import by.clevertec.test.lobacevich.bank.dao.BankDao;
import by.clevertec.test.lobacevich.bank.dao.UserDao;
import by.clevertec.test.lobacevich.bank.di.Dependency;
import by.clevertec.test.lobacevich.bank.di.Singleton;
import by.clevertec.test.lobacevich.bank.entity.Account;
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
public class AccountDaoImpl extends AbstractDao<Account> implements AccountDao {

    @Dependency(implementation = "UserDaoImpl")
    private UserDao userDao;
    @Dependency(implementation = "BankDaoImpl")
    private BankDao bankDao;
    private static final String CREATE_ACCOUNT = "INSERT INTO accounts(user_id, bank_id, account_number, " +
            "creation_date, balance, last_interest) VALUES(?, ?, ?, ?, ?, ?);";
    private static final String UPDATE_ACCOUNT = "UPDATE accounts SET user_id=?, bank_id=?, " +
            "account_number=?, creation_date=?, balance=?, last_interest=? WHERE id=?;";
    private static final String DELETE_ACCOUNT = "DELETE FROM accounts WHERE id=?;";
    private static final String GET_BY_ID = "SELECT * FROM accounts WHERE id=?";
    private static final String GET_BANK_ACCOUNTS = "SELECT * FROM accounts WHERE bank_id=?";
    private static final String GET_BY_NUMBER = "SELECT * FROM accounts WHERE account_number=?";
    private static final String GET_ALL = "SELECT * FROM accounts";

    /**
     * создает запись в бд из объекта
     * @param account объект счета в банке
     * @param connection подсоединение к бд
     * @throws DataBaseException в случае, если не удается связаться с бд пробрасывает в слой сервисов исключение
     */
    @Override
    public void createEntity(Account account, Connection connection) throws DataBaseException {
        try (PreparedStatement ps = connection.prepareStatement(CREATE_ACCOUNT)) {
            ps.setLong(1, account.getUser().getId());
            ps.setLong(2, account.getBank().getId());
            ps.setString(3, account.getAccountNumber());
            ps.setDate(4, Date.valueOf(account.getCreationDate()));
            ps.setBigDecimal(5, account.getBalance());
            ps.setObject(6, account.getLastInterest());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException("DB failed: Can't create account");
        }
    }

    /**
     * обновляет запись объекта в бж
     * @param account объект счета в банке, который надо обновить
     * @param connection соединение с бд
     * @throws DataBaseException в случае, если не удается связаться с бд пробрасывает в слой сервисов исключение
     */
    @Override
    public void updateEntity(Account account, Connection connection) throws DataBaseException {
        try (PreparedStatement ps = connection.prepareStatement(UPDATE_ACCOUNT)) {
            ps.setLong(1, account.getUser().getId());
            ps.setLong(2, account.getBank().getId());
            ps.setString(3, account.getAccountNumber());
            ps.setDate(4, Date.valueOf(account.getCreationDate()));
            ps.setBigDecimal(5, account.getBalance());
            ps.setObject(6, account.getLastInterest());
            ps.setLong(7, account.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException("DB failed: Can't update account");
        }
    }

    /**
     * удаляет запись из бд
     * @param account объект счета в банке, который надо удалить
     * @param connection соединение с бд
     * @throws DataBaseException в случае, если не удается связаться с бд пробрасывает в слой сервисов исключение
     */
    @Override
    public void deleteEntity(Account account, Connection connection) throws DataBaseException {
        try (PreparedStatement ps = connection.prepareStatement(DELETE_ACCOUNT)) {
            ps.setLong(1, account.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException("DB failed: Can't delete account");
        }
    }

    /**
     * получает сущность из бд
     * @param id сущности, которую надо получить
     * @param connection соединение с бд
     * @return готовый объект, айди которого был в параметрах
     * @throws DataBaseException в случае, если не удается связаться с бд пробрасывает в слой сервисов исключение
     */
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

    /**
     * по ответу из бд формирует объект
     * @param rs ответ из бд
     * @param connection соединение с бд
     * @return объект счета в банке
     * @throws DataBaseException в случае, если не удается связаться с бд пробрасывает в слой сервисов исключение
     */
    private Account resultSetToAccount(ResultSet rs, Connection connection) throws DataBaseException {
        try {
            Account account = new Account(rs.getLong("id"));
            account.setUser(userDao.getEntityById(rs.getLong("user_id"), connection));
            account.setBank(bankDao.getEntityById(rs.getLong("bank_id"), connection));
            account.setAccountNumber(rs.getString("account_number"));
            account.setCreationDate(rs.getDate("creation_date").toLocalDate());
            account.setBalance(rs.getBigDecimal("balance"));
            account.setLastInterest(rs.getObject("last_interest", LocalDateTime.class));
            return account;
        } catch (SQLException e) {
            throw new DataBaseException("DB failed: Can't load account");
        }
    }

    /**
     * выдает все аккаунты банка
     * @param bank_id айди банка
     * @param connection соединение с бд
     * @return объекты всех счетов в данном банке
     * @throws DataBaseException в случае, если не удается связаться с бд пробрасывает в слой сервисов исключение
     */
    @Override
    public List<Account> getBankAccounts(Long bank_id, Connection connection) throws DataBaseException {
        List<Account> accounts = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(GET_BANK_ACCOUNTS)) {
            ps.setLong(1, bank_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Account account = resultSetToAccount(rs, connection);
                accounts.add(account);
            }
            return accounts;
        } catch (SQLException e) {
            throw new DataBaseException("DB failed: Can't load accounts");
        }
    }

    /**
     * выдает объект счета в банке по номеру счета
     * @param accountNumber номер счета
     * @param connection соединение с бд
     * @return объект счета
     * @throws DataBaseException в случае, если не удается связаться с бд пробрасывает в слой сервисов исключение
     */
    @Override
    public Account getAccountByNumber(String accountNumber, Connection connection) throws DataBaseException {
        try (PreparedStatement ps = connection.prepareStatement(GET_BY_NUMBER)) {
            ps.setString(1, accountNumber);
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

    /**
     * возвращает все счета
     * @param connection соединение с бд
     * @return все счета из бд
     * @throws DataBaseException в случае, если не удается связаться с бд пробрасывает в слой сервисов исключение
     */
    @Override
    public List<Account> getAllEntities(Connection connection) throws DataBaseException {
        List<Account> accounts = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(GET_ALL)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Account account = resultSetToAccount(rs, connection);
                accounts.add(account);
            }
            return accounts;
        } catch (SQLException e) {
            throw new DataBaseException("DB failed: Can't load accounts");
        }
    }
}
