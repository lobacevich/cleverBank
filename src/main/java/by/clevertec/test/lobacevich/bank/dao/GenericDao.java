package by.clevertec.test.lobacevich.bank.dao;

import by.clevertec.test.lobacevich.bank.entity.Entity;
import by.clevertec.test.lobacevich.bank.exception.DataBaseException;

import java.sql.Connection;

public interface GenericDao<T extends Entity> {

    void createEntity(T t, Connection connection) throws DataBaseException;

    void updateEntity(T t, Connection connection) throws DataBaseException;

    void deleteEntity(T t, Connection connection) throws DataBaseException;

    T getEntityById(long id, Connection connection) throws DataBaseException;
}
