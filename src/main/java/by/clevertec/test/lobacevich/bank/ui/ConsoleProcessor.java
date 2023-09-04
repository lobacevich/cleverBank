package by.clevertec.test.lobacevich.bank.ui;

import by.clevertec.test.lobacevich.bank.di.Singleton;

import java.util.Scanner;

/**
 * класс, который отвечает за взаимодествие с пользователем через консоль
 */
@Singleton
public class ConsoleProcessor {

    /**
     * получает на вход целое число. в случае некорректного ввода выдает соответственное сообщение
     * @return введенное число
     */
    public int getIntInput() {
        Scanner sc = new Scanner(System.in);
        int number;
        while (true) {
            String input = sc.next();
            try {
                number = Integer.parseInt(input);
                return number;
            } catch (NumberFormatException e) {
                System.out.println("Некорректный ввод, попробуйте еще раз");
            }
        }
    }

    /**
     * получает на вход дробное число. в случае некорректного ввода выдает соответственное сообщение
     * @return введенное число
     */
    public Double getDoubleInput() {
        Scanner sc = new Scanner(System.in);
        double number;
        while (true) {
            String input = sc.next();
            try {
                number = Double.parseDouble(input);
                return number;
            } catch (NumberFormatException e) {
                System.out.println("Некорректный ввод, попробуйте еще раз");
            }
        }
    }
}

