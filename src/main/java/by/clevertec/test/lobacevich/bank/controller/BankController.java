package by.clevertec.test.lobacevich.bank.controller;

import by.clevertec.test.lobacevich.bank.di.Dependency;
import by.clevertec.test.lobacevich.bank.di.Singleton;
import by.clevertec.test.lobacevich.bank.exception.DataBaseException;
import by.clevertec.test.lobacevich.bank.service.BankService;

import java.util.List;

@Singleton
public class BankController {

    @Dependency(implementation = "BankServiceImpl")
    private BankService bankService;

    public List<String> getBankNames() {
        try {
            return bankService.getBankNames();
        } catch (DataBaseException e) {
            System.out.println(e);
        }
        return null;
    }
}
