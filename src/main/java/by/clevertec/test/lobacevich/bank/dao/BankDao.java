package by.clevertec.test.lobacevich.bank.dao;

import by.clevertec.test.lobacevich.bank.entity.Bank;
import by.clevertec.test.lobacevich.bank.exception.DataBaseException;

import java.sql.Connection;
import java.util.List;

public interface BankDao extends GenericDao<Bank> {
    List<Bank> getAllEntities(Connection connection) throws DataBaseException;

    Bank getBankByName(String name, Connection connection) throws DataBaseException;
}
