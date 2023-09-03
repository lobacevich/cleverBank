package by.clevertec.test.lobacevich.bank.service.impl;

import by.clevertec.test.lobacevich.bank.dao.AccountDao;
import by.clevertec.test.lobacevich.bank.dao.TransactionDao;
import by.clevertec.test.lobacevich.bank.db.Connect;
import by.clevertec.test.lobacevich.bank.di.Dependency;
import by.clevertec.test.lobacevich.bank.di.Singleton;
import by.clevertec.test.lobacevich.bank.entity.Account;
import by.clevertec.test.lobacevich.bank.entity.Transaction;
import by.clevertec.test.lobacevich.bank.exception.DataBaseException;
import by.clevertec.test.lobacevich.bank.exception.NotEnoughtFundsException;
import by.clevertec.test.lobacevich.bank.service.TransactionService;

import java.math.BigDecimal;
import java.sql.Connection;

@Singleton
public class TransactionServiceImpl implements TransactionService {

    private static final Connection CONNECTION = Connect.getConnection();
    @Dependency(implementation = "TransactionDaoImpl")
    private TransactionDao transactionDao;
    @Dependency(implementation = "AccountDaoImpl")
    private AccountDao accountDao;

    @Override
    public void topUpAccount(String accountNumber, Double sum) throws DataBaseException {
        Account account = accountDao.getAccountByNumber(accountNumber, CONNECTION);
        account.setBalance(account.getBalance().add(BigDecimal.valueOf(sum)));
        Transaction transaction = new Transaction(null, account, BigDecimal.valueOf(sum));
        accountDao.updateEntity(account, CONNECTION);
        transactionDao.createEntity(transaction, CONNECTION);
    }

    @Override
    public void withdrawFunds(String accountNumber, Double sum)
            throws DataBaseException, NotEnoughtFundsException {
        Account account = accountDao.getAccountByNumber(accountNumber, CONNECTION);
        if (account.getBalance().compareTo(BigDecimal.valueOf(sum)) >= 0) {
            account.setBalance(account.getBalance().subtract(BigDecimal.valueOf(sum)));
            Transaction transaction = new Transaction(account, null, BigDecimal.valueOf(sum));
            accountDao.updateEntity(account, CONNECTION);
            transactionDao.createEntity(transaction, CONNECTION);
        } else {
            throw new NotEnoughtFundsException("Transaction failed: not enough funds in the account");
        }
    }
}
