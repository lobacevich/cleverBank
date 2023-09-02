package by.clevertec.test.lobacevich.bank.service.impl;

import by.clevertec.test.lobacevich.bank.dao.AccountDao;
import by.clevertec.test.lobacevich.bank.dao.BankDao;
import by.clevertec.test.lobacevich.bank.dao.impl.AccountDaoImpl;
import by.clevertec.test.lobacevich.bank.dao.impl.BankDaoImpl;
import by.clevertec.test.lobacevich.bank.db.Connect;
import by.clevertec.test.lobacevich.bank.dto.AccountDto;
import by.clevertec.test.lobacevich.bank.entity.Account;
import by.clevertec.test.lobacevich.bank.entity.Bank;
import by.clevertec.test.lobacevich.bank.exception.DataBaseException;
import by.clevertec.test.lobacevich.bank.mapper.AccountMapper;
import by.clevertec.test.lobacevich.bank.service.AccountService;

import java.sql.Connection;
import java.util.List;
import java.util.stream.Collectors;

public class AccountServiceImpl implements AccountService {

    private static final AccountServiceImpl INSTANCE = new AccountServiceImpl();
    private Connection connection = Connect.getConnection();
    AccountDao accountDao = AccountDaoImpl.getInstance();
    AccountMapper accountMapper = AccountMapper.getINSTANCE();
    BankDao bankDao = BankDaoImpl.getInstance();

    private AccountServiceImpl() {
    }

    public static AccountServiceImpl getINSTANCE() {
        return INSTANCE;
    }

    @Override
    public List<AccountDto> getBankAccountDtos(String bankName) throws DataBaseException {
        Bank bank = bankDao.getBankByName(bankName, connection);
        return accountDao.getBankAccounts(bank.getId(), connection).stream()
                .map(accountMapper::accountToDto).collect(Collectors.toList());
    }

    @Override
    public AccountDto getAccountDtoByNumber(String accountNumber) throws DataBaseException {
        Account account = accountDao.getAccountByNumber(accountNumber, connection);
        return accountMapper.accountToDto(account);
    }
}
