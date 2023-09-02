package by.clevertec.test.lobacevich.bank.ui;

import by.clevertec.test.lobacevich.bank.controller.AccountController;
import by.clevertec.test.lobacevich.bank.controller.BankController;
import by.clevertec.test.lobacevich.bank.controller.TransactionController;
import by.clevertec.test.lobacevich.bank.dto.AccountDto;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Builder {

    @Getter
    private final Menu rootMenu = new Menu("Выберете банк", new ArrayList<>());
    private Menu accountMenu = new Menu("Выберете действие", new ArrayList<>());
    private Menu bankMenu = new Menu("Выберете аккаунт", new ArrayList<>());
    private ConsoleProcessor console = ConsoleProcessor.getInstance();
    private TransactionController transactionController = TransactionController.getInstance();
    private BankController bankController = BankController.getINSTANCE();
    private AccountController accountController = AccountController.getINSTANCE();
    private Navigator navigator = Navigator.getINSTANCE();
    private String accountNumber;
    private static final Builder INSTANCE = new Builder();

    private Builder() {
    }

    public static Builder getINSTANCE() {
        return INSTANCE;
    }

    public void buildRootMenu() {
        List<String> bankNames = bankController.getBankNames();
        for (int i = 0; i < bankNames.size(); i++) {
            String bankName = bankNames.get(i);
            MenuItem bankList = new MenuItem(bankName, () -> buildBankMenu(bankName), bankMenu);
            rootMenu.addItem(bankList);
        }
    }

    public void buildBankMenu(String bankName) {
        bankMenu.getMenuItems().clear();
        List<AccountDto> accountDtoList = accountController.getBankAccountDtos(bankName);
        for (int i = 0; i < accountDtoList.size(); i++) {
            AccountDto accountDto = accountDtoList.get(i);
            String title = accountDto.getAccountNumber() + "\t" + accountDto.getBalance() + "\t" +
                    accountDto.getFirstname() + " " + accountDto.getLastname() + " " +
                    accountDto.getSurname();
            MenuItem accountList = new MenuItem(title, () -> buildAccountMenu(accountDto), accountMenu);
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
        MenuItem transferMoney = new MenuItem("Перевести деньги на другой счет", () -> {
            System.out.println("Еще не реализовано");
        }, accountMenu);
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
