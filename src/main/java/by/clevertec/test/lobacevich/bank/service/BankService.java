package by.clevertec.test.lobacevich.bank.service;

import by.clevertec.test.lobacevich.bank.exception.DataBaseException;

import java.util.List;

public interface BankService {
    List<String> getBankNames() throws DataBaseException;
}
