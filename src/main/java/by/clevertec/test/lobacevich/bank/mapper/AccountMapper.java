package by.clevertec.test.lobacevich.bank.mapper;

import by.clevertec.test.lobacevich.bank.dto.AccountDto;
import by.clevertec.test.lobacevich.bank.entity.Account;

public class AccountMapper {

    private static final AccountMapper INSTANCE = new AccountMapper();

    public AccountMapper() {
    }

    public static AccountMapper getINSTANCE() {
        return INSTANCE;
    }

    public AccountDto accountToDto(Account account) {
        return AccountDto.builder()
                .accountNumber(account.getAccountNumber())
                .firstname(account.getUser().getFirstname())
                .lastname(account.getUser().getLastname())
                .surname(account.getUser().getSurname())
                .balance(account.getBalance().doubleValue())
                .build();
    }
}
