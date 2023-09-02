package by.clevertec.test.lobacevich.bank.ui;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Scanner;

public class ConsoleProcessor {

    private static ConsoleProcessor INSTANCE = new ConsoleProcessor();

    private ConsoleProcessor() {
    }

    public static ConsoleProcessor getInstance() {
        return INSTANCE;
    }

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

    public String getStringInput() {
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

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

    public LocalDate getDateInput() {
        while (true) {
            try {
                System.out.println("Enter day");
                int day = getIntInput();
                System.out.println("Enter month");
                int month = getIntInput();
                System.out.println("Enter year");
                int year = getIntInput();
                return LocalDate.of(year, month, day);
            } catch (DateTimeException e) {
                System.out.println("Incorrect data entered, please try again");
            }
        }
    }
}
