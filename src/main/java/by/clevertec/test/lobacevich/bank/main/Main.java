package by.clevertec.test.lobacevich.bank.main;

import by.clevertec.test.lobacevich.bank.entity.Bank;

public class Main {
    public static void main(String[] args) {

        Bank cleverBank = new Bank("Clever-Bank", "Minsk");
        System.out.println(cleverBank.getId());
        System.out.println(cleverBank);
    }
}
