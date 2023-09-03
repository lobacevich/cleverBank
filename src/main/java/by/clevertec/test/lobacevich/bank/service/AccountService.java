package by.clevertec.test.lobacevich.bank.service;

import by.clevertec.test.lobacevich.bank.dto.AccountDto;
import by.clevertec.test.lobacevich.bank.exception.DataBaseException;

import java.util.List;

public interface AccountService {

    List<AccountDto> getBankAccountDtos(String bankName) throws DataBaseException;

    AccountDto getAccountDtoByNumber(String accountNumber) throws DataBaseException;

    void checkAccountsInterest();
}
