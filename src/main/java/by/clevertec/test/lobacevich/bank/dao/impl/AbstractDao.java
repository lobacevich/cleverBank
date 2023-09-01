package by.clevertec.test.lobacevich.bank.dao.impl;

import by.clevertec.test.lobacevich.bank.dao.GenericDao;
import by.clevertec.test.lobacevich.bank.entity.Entity;
import by.clevertec.test.lobacevich.bank.exception.DataBaseException;

import java.sql.Connection;
import java.util.List;

public class AbstractDao<T extends Entity> implements GenericDao<T> {

    @Override
    public void createEntity(T t, Connection connection) throws DataBaseException {
    }

    @Override
    public void updateEntity(T t, Connection connection) throws DataBaseException {
    }

    @Override
    public void deleteEntity(T t, Connection connection) throws DataBaseException {
    }

    @Override
    public T getEntityById(long id, Connection connection) throws DataBaseException {
        return null;
    }
}
