package by.clevertec.test.lobacevich.bank.ui;

import by.clevertec.test.lobacevich.bank.controller.AccountController;
import by.clevertec.test.lobacevich.bank.controller.BankController;
import by.clevertec.test.lobacevich.bank.controller.TransactionController;
import by.clevertec.test.lobacevich.bank.di.Dependency;
import by.clevertec.test.lobacevich.bank.di.Singleton;
import by.clevertec.test.lobacevich.bank.dto.AccountDto;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class Builder {

    @Getter
    private final Menu rootMenu = new Menu("Выберете банк", new ArrayList<>());
    private final Menu accountMenu = new Menu("Выберете действие", new ArrayList<>());
    private final Menu bankMenu = new Menu("Выберете аккаунт", new ArrayList<>());
    @Dependency
    private ConsoleProcessor console;
    @Dependency
    private TransactionController transactionController;
    @Dependency
    private BankController bankController;
    @Dependency
    private AccountController accountController;
    AccountDto accountSender;

    public void buildRootMenu() {
        List<String> bankNames = bankController.getBankNames();
        for (String bankName : bankNames) {
            MenuItem bankList = new MenuItem(bankName, () -> buildBankMenu(bankName), bankMenu);
            rootMenu.addItem(bankList);
        }
    }

    public void buildBankMenu(String bankName) {
        bankMenu.getMenuItems().clear();
        List<AccountDto> accountDtoList = accountController.getBankAccountDtos(bankName);
        for (AccountDto accountDto : accountDtoList) {
            String title = accountDto.getAccountNumber() + "\t" + accountDto.getBalance() + "\t" +
                    accountDto.getFirstname() + " " + accountDto.getLastname() + " " +
                    accountDto.getSurname();
            MenuItem accountList = new MenuItem(title, () -> {
                if (accountSender == null) {
                    buildAccountMenu(accountDto);
                } else {
                    System.out.println("Введите сумму перевода");
                    Double sum = console.getDoubleInput();
                    System.out.println(transactionController.makeTransfer(accountSender.getAccountNumber(),
                            accountDto.getAccountNumber(), sum));
                    buildAccountMenu(accountSender);
                    accountSender = null;
                }
            }, accountMenu);
            bankMenu.addItem(accountList);
        }
    }

    public void buildAccountMenu(AccountDto accountDto) {
        accountMenu.getMenuItems().clear();
        MenuItem topUpAccount = new MenuItem("Пополнить счет", () -> {
            System.out.println("Введите сумму пополнения");
            Double sum = console.getDoubleInput();
            System.out.println(transactionController.topUpAccount(accountDto.getAccountNumber(), sum));
        }, accountMenu);
        accountMenu.addItem(topUpAccount);
        MenuItem withdrawFunds = new MenuItem("Снять средства со счета", () -> {
            System.out.println("Введите сумму снятия");
            Double sum = console.getDoubleInput();
            System.out.println(transactionController.withdrawFunds(accountDto.getAccountNumber(), sum));
        }, accountMenu);
        accountMenu.addItem(withdrawFunds);
        MenuItem transferMoney = new MenuItem("Перевести деньги на другой счет", () ->
                accountSender = accountDto, rootMenu);
        accountMenu.addItem(transferMoney);
        MenuItem getAccountInformation = new MenuItem("Получить информацию о счете", () -> {
            AccountDto newAccountDto = accountController.getAccountDto(accountDto.getAccountNumber());
            System.out.println(newAccountDto.getAccountNumber() + "\t" + newAccountDto.getBalance() + "\t" +
                    newAccountDto.getFirstname() + " " + newAccountDto.getLastname() + " " +
                    newAccountDto.getSurname());
        }, accountMenu);
        accountMenu.addItem(getAccountInformation);
        MenuItem changeAccount = new MenuItem("Выбрать другой счет", () -> {
        }, rootMenu);
        accountMenu.addItem(changeAccount);
    }
}
