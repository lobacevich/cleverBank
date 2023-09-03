package by.clevertec.test.lobacevich.bank.dao;

import by.clevertec.test.lobacevich.bank.entity.Account;
import by.clevertec.test.lobacevich.bank.exception.DataBaseException;

import java.sql.Connection;
import java.util.List;

public interface AccountDao extends GenericDao<Account> {

    List<Account> getBankAccounts(Long bank_id, Connection connection) throws DataBaseException;

    Account getAccountByNumber(String accountNumber, Connection connection) throws DataBaseException;

    List<Account> getAllEntities(Connection connection) throws DataBaseException;
}
