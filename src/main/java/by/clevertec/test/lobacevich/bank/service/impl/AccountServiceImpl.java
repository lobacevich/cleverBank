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

import java.sql.Connection;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class AccountServiceImpl implements AccountService {

    private static final Connection CONNECTION = Connect.getConnection();
    @Dependency(implementation = "AccountDaoImpl")
    AccountDao accountDao;
    @Dependency
    AccountMapper accountMapper;
    @Dependency(implementation = "BankDaoImpl")
    BankDao bankDao;

    @Override
    public List<AccountDto> getBankAccountDtos(String bankName) throws DataBaseException {
        Bank bank = bankDao.getBankByName(bankName, CONNECTION);
        return accountDao.getBankAccounts(bank.getId(), CONNECTION).stream()
                .map(accountMapper::accountToDto).collect(Collectors.toList());
    }

    @Override
    public AccountDto getAccountDtoByNumber(String accountNumber) throws DataBaseException {
        Account account = accountDao.getAccountByNumber(accountNumber, CONNECTION);
        return accountMapper.accountToDto(account);
    }
}
