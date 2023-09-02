package by.clevertec.test.lobacevich.bank.service.impl;

import by.clevertec.test.lobacevich.bank.dao.AccountDao;
import by.clevertec.test.lobacevich.bank.dao.TransactionDao;
import by.clevertec.test.lobacevich.bank.dao.impl.AccountDaoImpl;
import by.clevertec.test.lobacevich.bank.dao.impl.TransactionDaoImpl;
import by.clevertec.test.lobacevich.bank.db.Connect;
import by.clevertec.test.lobacevich.bank.entity.Account;
import by.clevertec.test.lobacevich.bank.entity.Transaction;
import by.clevertec.test.lobacevich.bank.exception.DataBaseException;
import by.clevertec.test.lobacevich.bank.exception.NotEnoughtFundsException;
import by.clevertec.test.lobacevich.bank.service.TransactionService;

import java.math.BigDecimal;
import java.sql.Connection;

public class TransactionServiceImpl implements TransactionService {

    private static final TransactionServiceImpl INSTANCE = new TransactionServiceImpl();
    private final Connection connection = Connect.getConnection();
    private final TransactionDao transactionDao = TransactionDaoImpl.getInstance();
    private final AccountDao accountDao = AccountDaoImpl.getInstance();

    private TransactionServiceImpl() {
    }

    public static TransactionServiceImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public void topUpAccount(String accountNumber, Double sum) throws DataBaseException {
        Account account = accountDao.getAccountByNumber(accountNumber, connection);
        account.setBalance(account.getBalance().add(BigDecimal.valueOf(sum)));
        Transaction transaction = new Transaction(null, account, BigDecimal.valueOf(sum));
        accountDao.updateEntity(account, connection);
        transactionDao.createEntity(transaction, connection);
    }

    @Override
    public void withdrawFunds(String accountNumber, Double sum)
            throws DataBaseException, NotEnoughtFundsException {
        Account account = accountDao.getAccountByNumber(accountNumber, connection);
        if (account.getBalance().compareTo(BigDecimal.valueOf(sum)) >= 0) {
            account.setBalance(account.getBalance().subtract(BigDecimal.valueOf(sum)));
            Transaction transaction = new Transaction(account, null, BigDecimal.valueOf(sum));
            accountDao.updateEntity(account, connection);
            transactionDao.createEntity(transaction, connection);
        } else {
            throw new NotEnoughtFundsException("Transaction failed: not enough funds in the account");
        }
    }
}
