package by.clevertec.test.lobacevich.bank.main;

import by.clevertec.test.lobacevich.bank.dao.AccountDao;
import by.clevertec.test.lobacevich.bank.dao.BankDao;
import by.clevertec.test.lobacevich.bank.dao.TransactionDao;
import by.clevertec.test.lobacevich.bank.dao.UserDao;
import by.clevertec.test.lobacevich.bank.dao.impl.AccountDaoImpl;
import by.clevertec.test.lobacevich.bank.dao.impl.BankDaoImpl;
import by.clevertec.test.lobacevich.bank.dao.impl.TransactionDaoImpl;
import by.clevertec.test.lobacevich.bank.dao.impl.UserDaoImpl;
import by.clevertec.test.lobacevich.bank.db.Connect;
import by.clevertec.test.lobacevich.bank.entity.Account;
import by.clevertec.test.lobacevich.bank.entity.Bank;
import by.clevertec.test.lobacevich.bank.entity.Transaction;
import by.clevertec.test.lobacevich.bank.entity.User;
import by.clevertec.test.lobacevich.bank.exception.DataBaseException;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws DataBaseException {

        Random random = new Random();

        Connection connection = Connect.getConnection();
        BankDao bankDao = BankDaoImpl.getInstance();
        UserDao userDao = UserDaoImpl.getInstance();
        AccountDao accountDao = AccountDaoImpl.getInstance();
        Account account = accountDao.getEntityById(1, connection);
        Account account1 = accountDao.getEntityById(3, connection);
        TransactionDao transactionDao = TransactionDaoImpl.getInstance();
        Transaction transaction = transactionDao.getEntityById(3, connection);
        transaction.setAccountSender(account);
        transaction.setAccountReceiver(null);
        transactionDao.deleteEntity(transaction, connection);
    }
}
