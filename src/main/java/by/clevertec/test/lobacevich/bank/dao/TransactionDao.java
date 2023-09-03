package by.clevertec.test.lobacevich.bank.dao;

import by.clevertec.test.lobacevich.bank.entity.Transaction;
import by.clevertec.test.lobacevich.bank.exception.DataBaseException;

import java.sql.Connection;
import java.util.List;

public interface TransactionDao extends GenericDao<Transaction> {
    List<Transaction> getAllEntities(Connection connection) throws DataBaseException;
}
