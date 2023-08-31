package by.clevertec.test.lobacevich.bank.main;

import by.clevertec.test.lobacevich.bank.db.Connect;

public class Main {
    public static void main(String[] args) {

        try {
            System.out.println(Connect.getConnection());
            System.out.println("Connection complete");
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
