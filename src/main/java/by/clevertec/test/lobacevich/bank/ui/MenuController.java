package by.clevertec.test.lobacevich.bank.ui;

import by.clevertec.test.lobacevich.bank.di.Dependency;
import by.clevertec.test.lobacevich.bank.di.Singleton;

@Singleton
public class MenuController {

    @Dependency
    private Builder builder;
    @Dependency
    private Navigator navigator;
    @Dependency
    private ConsoleProcessor consoleProcessor;

    public void run() {
        builder.buildRootMenu();
        navigator.setCurrentMenu(builder.getRootMenu());
        boolean flag = true;
        while (flag) {
            navigator.printMenu();
            Integer index = consoleProcessor.getIntInput();
            try {
                flag = navigator.navigate(index);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Incorrect input, please try again");
            }
        }
    }
}
