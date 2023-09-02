package by.clevertec.test.lobacevich.bank.main;

import by.clevertec.test.lobacevich.bank.controller.AccountController;
import by.clevertec.test.lobacevich.bank.exception.DataBaseException;
import by.clevertec.test.lobacevich.bank.service.AccountService;
import by.clevertec.test.lobacevich.bank.service.impl.AccountServiceImpl;
import by.clevertec.test.lobacevich.bank.ui.MenuController;

public class Main {

    static MenuController menuController = MenuController.getInstance();
    static AccountController accountController = AccountController.getINSTANCE();
    static AccountService accountService = AccountServiceImpl.getINSTANCE();
    public static void main(String[] args) throws DataBaseException {

        menuController.run();
    }
}
