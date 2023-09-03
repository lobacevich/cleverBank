package by.clevertec.test.lobacevich.bank.controller;

import by.clevertec.test.lobacevich.bank.di.Dependency;
import by.clevertec.test.lobacevich.bank.di.Singleton;
import by.clevertec.test.lobacevich.bank.dto.AccountDto;
import by.clevertec.test.lobacevich.bank.exception.DataBaseException;
import by.clevertec.test.lobacevich.bank.service.AccountService;

import java.util.List;

@Singleton
public class AccountController {

    @Dependency(implementation = "AccountServiceImpl")
    private AccountService accountService;

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
