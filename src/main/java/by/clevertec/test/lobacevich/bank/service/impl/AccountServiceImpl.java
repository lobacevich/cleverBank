package by.clevertec.test.lobacevich.bank.service.impl;

import by.clevertec.test.lobacevich.bank.dao.AccountDao;
import by.clevertec.test.lobacevich.bank.dao.BankDao;
import by.clevertec.test.lobacevich.bank.db.Connect;
import by.clevertec.test.lobacevich.bank.di.Dependency;
import by.clevertec.test.lobacevich.bank.di.Singleton;
import by.clevertec.test.lobacevich.bank.dto.AccountDto;
import by.clevertec.test.lobacevich.bank.entity.Account;
import by.clevertec.test.lobacevich.bank.entity.Bank;
import by.clevertec.test.lobacevich.bank.exception.DataBaseException;
import by.clevertec.test.lobacevich.bank.mapper.AccountMapper;
import by.clevertec.test.lobacevich.bank.service.AccountService;
import by.clevertec.test.lobacevich.bank.util.YamlReader;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * сервис, содержащий функции объектов счет в банке
 */
@Singleton
public class AccountServiceImpl implements AccountService {

    private static final Connection CONNECTION = Connect.getConnection();
    @Dependency(implementation = "AccountDaoImpl")
    AccountDao accountDao;
    @Dependency
    AccountMapper accountMapper;
    @Dependency(implementation = "BankDaoImpl")
    BankDao bankDao;

    /**
     * получить ДТО счетов в данном банке
     * @param bankName название банка
     * @return дто счетов
     * @throws DataBaseException в случае, если не удается связаться с бд пробрасывает в слой контроллеров исключение
     */
    @Override
    public List<AccountDto> getBankAccountDtos(String bankName) throws DataBaseException {
        Bank bank = bankDao.getBankByName(bankName, CONNECTION);
        return accountDao.getBankAccounts(bank.getId(), CONNECTION).stream()
                .map(accountMapper::accountToDto).collect(Collectors.toList());
    }

    /**
     * получить ДТО счета по номеру
     * @param accountNumber номер счета
     * @return ДТО счета
     * @throws DataBaseException в случае, если не удается связаться с бд пробрасывает в слой контроллеров исключение
     */
    @Override
    public AccountDto getAccountDtoByNumber(String accountNumber) throws DataBaseException {
        Account account = accountDao.getAccountByNumber(accountNumber, CONNECTION);
        return accountMapper.accountToDto(account);
    }

    /**
     * проверяет, надо ли начислять проценты на остаток средств по всем аккаунтам
     */
    @Override
    public void checkAccountsInterest() {
        try {
            List<Account> accounts = accountDao.getAllEntities(CONNECTION);
            for (Account account : accounts) {
                checkAccount(account);
            }
        } catch (DataBaseException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * проверяет, надо ли начислять проценты на остаток средств
     * @param account объект аккаунта
     * @throws IOException если происходит ошибка чтения
     * @throws DataBaseException в случае, если не удается связаться с бд пробрасывает в слой контроллеров исключение
     */
    private void checkAccount(Account account) throws IOException, DataBaseException {
        if (account.getLastInterest().isBefore(LocalDateTime.now().minusMonths(1))) {
            accrueInterest(account);
        }
    }

    /**
     * начисляет проценты на счет
     * @param account объект счет в банке
     * @throws IOException если происходит ошибка чтения
     * @throws DataBaseException в случае, если не удается связаться с бд пробрасывает в слой контроллеров исключение
     */
    private void accrueInterest(Account account) throws IOException, DataBaseException {
        int percent = (int) YamlReader.getMap().get("percent");
        BigDecimal result = account.getBalance().multiply(BigDecimal.valueOf(100 + percent))
                .divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP);
        account.setBalance(result);
        account.setLastInterest(LocalDateTime.now());
        accountDao.updateEntity(account, CONNECTION);
    }
}
