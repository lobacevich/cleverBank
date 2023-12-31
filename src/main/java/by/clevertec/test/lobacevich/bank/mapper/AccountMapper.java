package by.clevertec.test.lobacevich.bank.mapper;

import by.clevertec.test.lobacevich.bank.di.Singleton;
import by.clevertec.test.lobacevich.bank.dto.AccountDto;
import by.clevertec.test.lobacevich.bank.entity.Account;

/**
 * преобразует объект в ДТО
 */
@Singleton
public class AccountMapper {

    /**
     * преобразует объект в ДТО
     * @param account объект счета в банке
     * @return ДТО объекта
     */
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
