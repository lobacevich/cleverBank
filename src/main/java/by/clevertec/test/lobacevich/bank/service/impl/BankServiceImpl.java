package by.clevertec.test.lobacevich.bank.service.impl;

import by.clevertec.test.lobacevich.bank.dao.BankDao;
import by.clevertec.test.lobacevich.bank.dao.impl.BankDaoImpl;
import by.clevertec.test.lobacevich.bank.db.Connect;
import by.clevertec.test.lobacevich.bank.entity.Bank;
import by.clevertec.test.lobacevich.bank.exception.DataBaseException;
import by.clevertec.test.lobacevich.bank.service.BankService;

import java.sql.Connection;
import java.util.List;
import java.util.stream.Collectors;

public class BankServiceImpl implements BankService {

    private static final BankServiceImpl INSTANCE = new BankServiceImpl();
    private final Connection connection = Connect.getConnection();
    private final BankDao bankDao = BankDaoImpl.getInstance();

    private BankServiceImpl() {
    }

    public static BankServiceImpl getINSTANCE() {
        return INSTANCE;
    }

    @Override
    public List<String> getBankNames() throws DataBaseException {
        List<Bank> banks = bankDao.getAllEntities(connection);
        return banks.stream().map(Bank::getName).collect(Collectors.toList());
    }
}
