package by.clevertec.test.lobacevich.bank.ui;

import by.clevertec.test.lobacevich.bank.di.Dependency;
import by.clevertec.test.lobacevich.bank.di.Singleton;

/**
 * класс, который запускает и контролирует взаимодействие через пользовательский интерфейс
 */
@Singleton
public class MenuController {

    @Dependency
    private Builder builder;
    @Dependency
    private Navigator navigator;
    @Dependency
    private ConsoleProcessor consoleProcessor;

    /**
     * запускает остальные методы классов пользовательского интерфейса и делает это до тех пор,
     * пока объект класса Navigator не вернет flag = false
     */
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
                System.out.println("Неверный ввод, попробуйте еще раз");
            }
        }
    }
}
