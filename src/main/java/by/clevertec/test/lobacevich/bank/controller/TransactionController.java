package by.clevertec.test.lobacevich.bank.controller;

import by.clevertec.test.lobacevich.bank.dto.AccountDto;
import by.clevertec.test.lobacevich.bank.entity.Account;
import by.clevertec.test.lobacevich.bank.exception.DataBaseException;
import by.clevertec.test.lobacevich.bank.exception.NotEnoughtFundsException;
import by.clevertec.test.lobacevich.bank.service.TransactionService;
import by.clevertec.test.lobacevich.bank.service.impl.TransactionServiceImpl;

public class TransactionController {

    private static final TransactionController INSTANCE = new TransactionController();

    private final TransactionService transactionService = TransactionServiceImpl.getInstance();

    private TransactionController() {
    }

    public static TransactionController getInstance() {
        return INSTANCE;
    }

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
