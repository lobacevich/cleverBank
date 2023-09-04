package by.clevertec.test.lobacevich.bank.runner;

import by.clevertec.test.lobacevich.bank.di.DependenciesHandler;
import by.clevertec.test.lobacevich.bank.di.Dependency;
import by.clevertec.test.lobacevich.bank.service.AccountService;
import by.clevertec.test.lobacevich.bank.ui.MenuController;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Класс, который запускает приложение
 * <p>
 *     Сперва запускается DependenciesHandler, который внедряет зависимости по всему приложению,
 *     затем создается планировщик, который каждые 30 секунд запускает метод checkAccountsInterest
 *     класса AccountService, после чего запускается консольное меню через метод run класса
 *     MenuController
 * </p>
 */

public class Main {

    @Dependency
    private static MenuController menuController;
    @Dependency(implementation = "AccountServiceImpl")
    private static AccountService accountService;

    public static void main(String[] args) {
        DependenciesHandler.injectDependencies();
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        Runnable task = () -> accountService.checkAccountsInterest();
        scheduler.scheduleAtFixedRate(task, 0, 30, TimeUnit.SECONDS);
        menuController.run();
        scheduler.shutdown();
    }
}
