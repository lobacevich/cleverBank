package by.clevertec.test.lobacevich.bank.controller;

import by.clevertec.test.lobacevich.bank.dto.AccountDto;
import by.clevertec.test.lobacevich.bank.exception.DataBaseException;
import by.clevertec.test.lobacevich.bank.service.AccountService;
import by.clevertec.test.lobacevich.bank.service.impl.AccountServiceImpl;

import java.util.List;

public class AccountController {

    private static final AccountController INSTANCE = new AccountController();
    private final AccountService accountService = AccountServiceImpl.getINSTANCE();

    private AccountController() {
    }

    public static AccountController getINSTANCE() {
        return INSTANCE;
    }

    public List<AccountDto> getBankAccountDtos(String bankName) {
        try {
            return accountService.getBankAccountDtos(bankName);
        } catch (DataBaseException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public AccountDto getAccountDto(String accountNumber) {
        try {
            return accountService.getAccountDtoByNumber(accountNumber);
        } catch (DataBaseException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
