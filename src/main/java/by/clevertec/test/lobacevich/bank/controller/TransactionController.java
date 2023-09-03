package by.clevertec.test.lobacevich.bank.controller;

import by.clevertec.test.lobacevich.bank.di.Dependency;
import by.clevertec.test.lobacevich.bank.di.Singleton;
import by.clevertec.test.lobacevich.bank.exception.DataBaseException;
import by.clevertec.test.lobacevich.bank.exception.NotEnoughtFundsException;
import by.clevertec.test.lobacevich.bank.service.TransactionService;

@Singleton
public class TransactionController {

    @Dependency(implementation = "TransactionServiceImpl")
    private TransactionService transactionService;

    public String topUpAccount(String accountNumber, Double sum) {
        try {
            transactionService.topUpAccount(accountNumber, sum);
            return "Account has been successfully funded";
        } catch (DataBaseException e) {
            return e.getMessage();
        }
    }

    public String withdrawFunds(String accountNumber, Double sum) {
        try {
            transactionService.withdrawFunds(accountNumber, sum);
            return "Funds have been successfully withdrawn";
        } catch (DataBaseException | NotEnoughtFundsException e) {
            return e.getMessage();
        }
    }
}
