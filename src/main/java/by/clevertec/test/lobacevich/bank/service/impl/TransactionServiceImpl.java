package by.clevertec.test.lobacevich.bank.service.impl;

import by.clevertec.test.lobacevich.bank.dao.AccountDao;
import by.clevertec.test.lobacevich.bank.dao.TransactionDao;
import by.clevertec.test.lobacevich.bank.db.Connect;
import by.clevertec.test.lobacevich.bank.di.Dependency;
import by.clevertec.test.lobacevich.bank.di.Singleton;
import by.clevertec.test.lobacevich.bank.entity.Account;
import by.clevertec.test.lobacevich.bank.entity.Transaction;
import by.clevertec.test.lobacevich.bank.exception.ConnectionException;
import by.clevertec.test.lobacevich.bank.exception.DataBaseException;
import by.clevertec.test.lobacevich.bank.exception.NotEnoughtFundsException;
import by.clevertec.test.lobacevich.bank.pdf.PdfGenerator;
import by.clevertec.test.lobacevich.bank.service.TransactionService;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * сервис, содержащий функции объектов транзакция
 */
@Singleton
public class TransactionServiceImpl implements TransactionService {

    private static final Connection CONNECTION = Connect.getConnection();
    @Dependency(implementation = "TransactionDaoImpl")
    private TransactionDao transactionDao;
    @Dependency(implementation = "AccountDaoImpl")
    private AccountDao accountDao;
    @Dependency
    private PdfGenerator pdfGenerator;

    /**
     * пополнить счет
     * @param accountNumber номер счета
     * @param sum сумма пополнения
     * @throws DataBaseException в случае, если не удается связаться с бд пробрасывает в слой контроллеров исключение
     */
    @Override
    public void topUpAccount(String accountNumber, Double sum) throws DataBaseException {
        Account account = accountDao.getAccountByNumber(accountNumber, CONNECTION);
        account.setBalance(account.getBalance().add(BigDecimal.valueOf(sum)));
        Transaction transaction = new Transaction(null, account, BigDecimal.valueOf(sum));
        try {
            CONNECTION.setAutoCommit(false);
            accountDao.updateEntity(account, CONNECTION);
            transactionDao.createEntity(transaction, CONNECTION);
            CONNECTION.commit();
            pdfGenerator.printCheck(transaction, getCheckNumber());
        } catch (SQLException | DataBaseException | IOException e) {
            try {
                CONNECTION.rollback();
            } catch (SQLException ex) {
                throw new ConnectionException("Ошибка соединения с БД");
            } finally {
                try {
                    CONNECTION.setAutoCommit(true);
                } catch (SQLException ex) {
                    throw new ConnectionException("Ошибка соединения с БД");
                }
            }
        }
    }

    /**
     * снять деньги со счета
     * @param accountNumber номер счета
     * @param sum сумм
     * @throws DataBaseException в случае, если не удается связаться с бд пробрасывает в слой контроллеров исключение
     * @throws NotEnoughtFundsException если не хватает денег на счете
     */
    @Override
    public void withdrawFunds(String accountNumber, Double sum)
            throws DataBaseException, NotEnoughtFundsException {
        Account account = accountDao.getAccountByNumber(accountNumber, CONNECTION);
        if (account.getBalance().compareTo(BigDecimal.valueOf(sum)) >= 0) {
            account.setBalance(account.getBalance().subtract(BigDecimal.valueOf(sum)));
            Transaction transaction = new Transaction(account, null, BigDecimal.valueOf(sum));
            try {
                CONNECTION.setAutoCommit(false);
                accountDao.updateEntity(account, CONNECTION);
                transactionDao.createEntity(transaction, CONNECTION);
                CONNECTION.commit();
                pdfGenerator.printCheck(transaction, getCheckNumber());
            } catch (SQLException | DataBaseException | IOException e) {
                try {
                    CONNECTION.rollback();
                } catch (SQLException ex) {
                    throw new ConnectionException("Ошибка соединения с БД");
                } finally {
                    try {
                        CONNECTION.setAutoCommit(true);
                    } catch (SQLException ex) {
                        throw new ConnectionException("Ошибка соединения с БД");
                    }
                }
            }
        } else {
            throw new NotEnoughtFundsException("Не достаточно средств на счете");
        }
    }

    /**
     * перевести деньги на другой счет
     * @param accountSenderNumber номер счета отправителя
     * @param accountReceiverNumber номер счета получателя
     * @param sum сумма
     * @throws DataBaseException  если не удается связаться с бд пробрасывает в слой контроллеров исключение
     * @throws NotEnoughtFundsException
     */
    @Override
    public void makeTransfer(String accountSenderNumber, String accountReceiverNumber, Double sum)
            throws DataBaseException, NotEnoughtFundsException {
        Account accountSender = accountDao.getAccountByNumber(accountSenderNumber, CONNECTION);
        Account accountReceiver = accountDao.getAccountByNumber(accountReceiverNumber, CONNECTION);
        if (accountSender.getBalance().compareTo(BigDecimal.valueOf(sum)) >= 0) {
            makeBankTransfer(accountSender, accountReceiver, sum);
        } else {
            throw new NotEnoughtFundsException("Не достаточно средств на счете");
        }
    }

    /**
     * перевести деньги на другой счет
     * @param accountSender счет отправителя
     * @param accountReceiver счет получателя
     * @param sum сумма
     */
    private void makeBankTransfer(Account accountSender, Account accountReceiver, Double sum) {
        accountSender.setBalance(accountSender.getBalance().subtract(BigDecimal.valueOf(sum)));
        accountReceiver.setBalance(accountReceiver.getBalance().add(BigDecimal.valueOf(sum)));
        Transaction transaction = new Transaction(accountSender, accountReceiver, BigDecimal.valueOf(sum));
        try {
            CONNECTION.setAutoCommit(false);
            accountDao.updateEntity(accountSender, CONNECTION);
            accountDao.updateEntity(accountReceiver, CONNECTION);
            transactionDao.createEntity(transaction, CONNECTION);
            CONNECTION.commit();
            pdfGenerator.printCheck(transaction, getCheckNumber());
        } catch (SQLException | DataBaseException e) {
            try {
                CONNECTION.rollback();
            } catch (SQLException ex) {
                throw new ConnectionException("Ошибка соединения с БД");
            } finally {
                try {
                    CONNECTION.setAutoCommit(true);
                } catch (SQLException ex) {
                    throw new ConnectionException("Ошибка соединения с БД");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * получить номер чека на основании номера последней транзакции
     * @return номер счета в формате строки
     * @throws DataBaseException в случае, если не удается связаться с бд пробрасывает в слой сервисов исключение
     */
    private String getCheckNumber() throws DataBaseException {
        List<Transaction> transactions = transactionDao.getAllEntities(CONNECTION);
        if (transactions.isEmpty()) {
            return "1";
        }
        Long checkNumber = transactions.get(transactions.size() - 1).getId() + 1;
        return checkNumber.toString();
    }
}
