package by.clevertec.test.lobacevich.bank.controller;

import by.clevertec.test.lobacevich.bank.di.Dependency;
import by.clevertec.test.lobacevich.bank.di.Singleton;
import by.clevertec.test.lobacevich.bank.exception.DataBaseException;
import by.clevertec.test.lobacevich.bank.exception.NotEnoughtFundsException;
import by.clevertec.test.lobacevich.bank.service.TransactionService;

/**
 * Класс, предназначенный для взаимодествия пользовательского интерфейса и слоя сервисов касаемо
 * транзакций
 */
@Singleton
public class TransactionController {

    @Dependency(implementation = "TransactionServiceImpl")
    private TransactionService transactionService;

    /**
     * Вызывает в слое сервисов метод для пополнения счета
     * @param accountNumber номер счета, который надо пополнить
     * @param sum сумма пополенения счета
     * @return сообщение об успешном пополнении либо сообщение об ошибке
     */
    public String topUpAccount(String accountNumber, Double sum) {
        try {
            transactionService.topUpAccount(accountNumber, sum);
            return "Средства были успешно зачислены";
        } catch (DataBaseException e) {
            return e.getMessage();
        }
    }

    /**
     * Вызывает в слое сервисов метод для снятия денежных средств со счета
     * @param accountNumber номер счета, с которого надо снять средства
     * @param sum сумма, которую необходимо снять со счета
     * @return сообщение об успешном снятии средств либо сообщение об ошибке
     */
    public String withdrawFunds(String accountNumber, Double sum) {
        try {
            transactionService.withdrawFunds(accountNumber, sum);
            return "Средства были успешно сняты";
        } catch (DataBaseException | NotEnoughtFundsException e) {
            return e.getMessage();
        }
    }

    /**
     * Вызывает в слое сервисов метод для перевода денежных средств между счетами
     * @param accountSenderNumber номер счета, с которого будет осуществляться перевод
     * @param accountReceiverNumber номер счета, на который будут переводиться денежные средства
     * @param sum сумма перевода
     * @return сообщение об успешном переводе средств либо сообщение об ошибке
     */
    public String makeTransfer(String accountSenderNumber, String accountReceiverNumber, Double sum) {
        try {
            transactionService.makeTransfer(accountSenderNumber, accountReceiverNumber, sum);
            return "Средства были успешно переведены";
        } catch (NotEnoughtFundsException | DataBaseException e) {
            return e.getMessage();
        }
    }
}
