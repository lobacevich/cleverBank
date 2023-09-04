package by.clevertec.test.lobacevich.bank.controller;

import by.clevertec.test.lobacevich.bank.di.Dependency;
import by.clevertec.test.lobacevich.bank.di.Singleton;
import by.clevertec.test.lobacevich.bank.dto.AccountDto;
import by.clevertec.test.lobacevich.bank.exception.DataBaseException;
import by.clevertec.test.lobacevich.bank.service.AccountService;

import java.util.List;

/**
 * Класс, предназначенный для взаимодествия пользовательского интерфейса и слоя сервисов касаемо
 * аккаунтов
 */
@Singleton
public class AccountController {

    @Dependency(implementation = "AccountServiceImpl")
    private AccountService accountService;

    /**
     * Получает из слоя сервисов список ДТО аккаунтов, находящихся в банке.
     * @param bankName название банка
     * @return ДТО объектов, находящихся в банке.
     * @throws if что-то пошло не так, выдает информацию об ошибке и кидает RuntimeException
     */
    public List<AccountDto> getBankAccountDtos(String bankName) {
        try {
            return accountService.getBankAccountDtos(bankName);
        } catch (DataBaseException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException();
        }
    }

    /**
     * Получает из слоя сервисов ДТО счета по номеру счета.
     * @param accountNumber номер счета, по которому необходимо получить ДТО
     * @return ДТО аккаунта
     * @throws if что-то пошло не так, выдает информацию об ошибке и кидает RuntimeException
     */
    public AccountDto getAccountDto(String accountNumber) {
        try {
            return accountService.getAccountDtoByNumber(accountNumber);
        } catch (DataBaseException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException();
        }
    }
}
