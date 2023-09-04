package by.clevertec.test.lobacevich.bank.dao.impl;

import by.clevertec.test.lobacevich.bank.dao.BankDao;
import by.clevertec.test.lobacevich.bank.di.Singleton;
import by.clevertec.test.lobacevich.bank.entity.Bank;
import by.clevertec.test.lobacevich.bank.exception.DataBaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * класс для преобразования объектов в запись бд и записи бд в объекты
 */
@Singleton
public class BankDaoImpl extends AbstractDao<Bank> implements BankDao {

    private static final String CREATE_BANK = "INSERT INTO banks(name, address) VALUES(?, ?);";
    private static final String UPDATE_BANK = "UPDATE banks SET name=?, address=? WHERE id=?;";
    private static final String DELETE_BANK = "DELETE FROM banks WHERE id=?;";
    private static final String GET_BY_ID = "SELECT * FROM banks WHERE id=?";
    private static final String GET_ALL = "SELECT * FROM banks";
    private static final String GET_BY_NAME = "SELECT * FROM banks WHERE name=?";

    /**
     * записать объект в бд
     * @param bank банк
     * @param connection соединение с бд
     * @throws DataBaseException в случае, если не удается связаться с бд пробрасывает в слой сервисов исключение
     */
    @Override
    public void createEntity(Bank bank, Connection connection) throws DataBaseException {
        try (PreparedStatement ps = connection.prepareStatement(CREATE_BANK)){
            ps.setString(1, bank.getName());
            ps.setString(2, bank.getAddress());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException("DB failed: Can't create bank");
        }
    }

    /**
     * обновляет запись объекта в бд
     * @param bank объект банк
     * @param connection соединение с бд
     * @throws DataBaseException в случае, если не удается связаться с бд пробрасывает в слой сервисов исключение
     */
    @Override
    public void updateEntity(Bank bank, Connection connection) throws DataBaseException {
        try (PreparedStatement ps = connection.prepareStatement(UPDATE_BANK)) {
            ps.setString(1, bank.getName());
            ps.setString(2, bank.getAddress());
            ps.setLong(3, bank.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException("DB failed: Can't update bank");
        }
    }

    /**
     * удаляет запись об объекте из бд
     * @param bank объект банка
     * @param connection соединение с бд
     * @throws DataBaseException в случае, если не удается связаться с бд пробрасывает в слой сервисов исключение
     */
    @Override
    public void deleteEntity(Bank bank, Connection connection) throws DataBaseException {
        try (PreparedStatement ps = connection.prepareStatement(DELETE_BANK)) {
            ps.setLong(1, bank.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException("DB failed: Can't delete bank");
        }
    }

    /**
     * получает объект класса по айди
     * @param id айди объекта
     * @param connection соединение с бд
     * @return объект класса банк
     * @throws DataBaseException в случае, если не удается связаться с бд пробрасывает в слой сервисов исключение
     */
    @Override
    public Bank getEntityById(long id, Connection connection) throws DataBaseException {
        try (PreparedStatement ps = connection.prepareStatement(GET_BY_ID)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return resultSetToBank(rs);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DataBaseException("DB failed: Can't get bank by id");
        }
    }

    /**
     * по ответу из бд формирует объект
     * @param rs ответ из бд
     * @return объект класса банк
     * @throws DataBaseException в случае, если не удается связаться с бд пробрасывает в слой сервисов исключение
     */
    private Bank resultSetToBank(ResultSet rs) throws DataBaseException {
        try {
            Bank bank = new Bank(rs.getLong("id"));
            bank.setName(rs.getString("name"));
            bank.setAddress(rs.getString("address"));
            return bank;
        } catch (SQLException e) {
            throw new DataBaseException("DB failed: Can't load bank");
        }
    }

    /**
     * возвращает список всех банков из бд
     * @param connection соединение с бд
     * @return список всех банков из бд
     * @throws DataBaseException в случае, если не удается связаться с бд пробрасывает в слой сервисов исключение
     */
    @Override
    public List<Bank> getAllEntities(Connection connection) throws DataBaseException {
        List<Bank> banks = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(GET_ALL)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Bank bank = resultSetToBank(rs);
                banks.add(bank);
            }
            return banks;
        } catch (SQLException e) {
            throw new DataBaseException("DB failed: Can't load banks");
        }
    }

    /**
     * возвращает объект банка по названию
     * @param name название банка
     * @param connection соединение с бд
     * @return объект банка
     * @throws DataBaseException в случае, если не удается связаться с бд пробрасывает в слой сервисов исключение
     */
    @Override
    public Bank getBankByName(String name, Connection connection) throws DataBaseException {
        try (PreparedStatement ps = connection.prepareStatement(GET_BY_NAME)) {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return resultSetToBank(rs);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DataBaseException("DB failed: Can't get bank by name");
        }
    }
}
