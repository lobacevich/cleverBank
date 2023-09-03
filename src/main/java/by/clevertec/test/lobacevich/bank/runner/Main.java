package by.clevertec.test.lobacevich.bank.runner;

import by.clevertec.test.lobacevich.bank.di.DependenciesHandler;
import by.clevertec.test.lobacevich.bank.di.Dependency;
import by.clevertec.test.lobacevich.bank.ui.MenuController;

public class Main {

    @Dependency
    private static MenuController menuController;

    public static void main(String[] args) {
        DependenciesHandler.injectDependencies();
        menuController.run();
    }
}
