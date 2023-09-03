package by.clevertec.test.lobacevich.bank.ui;

import by.clevertec.test.lobacevich.bank.di.Singleton;

import java.util.Scanner;

@Singleton
public class ConsoleProcessor {

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
}

