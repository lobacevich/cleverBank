package by.clevertec.test.lobacevich.bank.service;

import by.clevertec.test.lobacevich.bank.entity.Account;
import by.clevertec.test.lobacevich.bank.exception.DataBaseException;
import by.clevertec.test.lobacevich.bank.exception.NotEnoughtFundsException;

public interface TransactionService {

    void topUpAccount(Account account, Double sum) throws DataBaseException;

    void withdrawFunds(Account account, Double sum)
            throws DataBaseException, NotEnoughtFundsException;
}
