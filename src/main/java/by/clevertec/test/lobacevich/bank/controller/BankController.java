package by.clevertec.test.lobacevich.bank.controller;

import by.clevertec.test.lobacevich.bank.exception.DataBaseException;
import by.clevertec.test.lobacevich.bank.service.BankService;
import by.clevertec.test.lobacevich.bank.service.impl.BankServiceImpl;

import java.util.List;

public class BankController {

    private static final BankController INSTANCE = new BankController();
    private BankService bankService = BankServiceImpl.getINSTANCE();

    private BankController() {
    }

    public static BankController getINSTANCE() {
        return INSTANCE;
    }

    public List<String> getBankNames() {
        try {
            return bankService.getBankNames();
        } catch (DataBaseException e) {
            System.out.println(e);
        }
        return null;
    }
}
