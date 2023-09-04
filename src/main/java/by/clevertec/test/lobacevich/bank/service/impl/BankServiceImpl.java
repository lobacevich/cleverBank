package by.clevertec.test.lobacevich.bank.service.impl;

import by.clevertec.test.lobacevich.bank.dao.BankDao;
import by.clevertec.test.lobacevich.bank.db.Connect;
import by.clevertec.test.lobacevich.bank.di.Dependency;
import by.clevertec.test.lobacevich.bank.di.Singleton;
import by.clevertec.test.lobacevich.bank.entity.Bank;
import by.clevertec.test.lobacevich.bank.exception.DataBaseException;
import by.clevertec.test.lobacevich.bank.service.BankService;

import java.sql.Connection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * сервис, содержащий функции объектов банк
 */
@Singleton
public class BankServiceImpl implements BankService {

    private static final Connection CONNECTION = Connect.getConnection();
    @Dependency(implementation = "BankDaoImpl")
    private BankDao bankDao;

    /**
     * возвращает названия всех банков
     * @return список названий банков
     * @throws DataBaseException в случае, если не удается связаться с бд пробрасывает в слой контроллеров исключение
     */
    @Override
    public List<String> getBankNames() throws DataBaseException {
        List<Bank> banks = bankDao.getAllEntities(CONNECTION);
        return banks.stream().map(Bank::getName).collect(Collectors.toList());
    }
}
