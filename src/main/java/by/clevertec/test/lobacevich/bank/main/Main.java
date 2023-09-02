package by.clevertec.test.lobacevich.bank.main;

import by.clevertec.test.lobacevich.bank.controller.TransactionController;
import by.clevertec.test.lobacevich.bank.dao.AccountDao;
import by.clevertec.test.lobacevich.bank.dao.TransactionDao;
import by.clevertec.test.lobacevich.bank.dao.impl.AccountDaoImpl;
import by.clevertec.test.lobacevich.bank.dao.impl.TransactionDaoImpl;
import by.clevertec.test.lobacevich.bank.db.Connect;
import by.clevertec.test.lobacevich.bank.entity.Account;
import by.clevertec.test.lobacevich.bank.exception.DataBaseException;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) throws DataBaseException {

        Connection connection = Connect.getConnection();
        AccountDao accountDao = AccountDaoImpl.getInstance();
        TransactionDao transactionDao = TransactionDaoImpl.getInstance();
        TransactionController transactionController = TransactionController.getInstance();

        Account account = accountDao.getEntityById(41, connection);
        transactionController.withdrawFunds(account, 1000.0);
    }
}
