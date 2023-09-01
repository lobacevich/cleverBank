package by.clevertec.test.lobacevich.bank.main;

import by.clevertec.test.lobacevich.bank.dao.BankDao;
import by.clevertec.test.lobacevich.bank.dao.UserDao;
import by.clevertec.test.lobacevich.bank.dao.impl.BankDaoImpl;
import by.clevertec.test.lobacevich.bank.dao.impl.UserDaoImpl;
import by.clevertec.test.lobacevich.bank.db.Connect;
import by.clevertec.test.lobacevich.bank.entity.Bank;
import by.clevertec.test.lobacevich.bank.entity.User;
import by.clevertec.test.lobacevich.bank.exception.DataBaseException;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {

        Connection connection = Connect.getConnection();
        BankDao bankDao = new BankDaoImpl();
        Bank bank = new Bank("Clever-Bank", "Minsk");
        try {
            bankDao.createEntity(bank, connection);
        } catch (DataBaseException e) {
            throw new RuntimeException(e);
        }
//        try {
//            Bank bank = bankDao.getEntityById(1, connection);
//            bank.setAddress("Dmitry");
//            bankDao.deleteEntity(bank, connection);
//        } catch (DataBaseException e) {
//            throw new RuntimeException(e);
//        }
    }
}
