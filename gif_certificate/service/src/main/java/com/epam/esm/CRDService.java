package com.epam.esm;

import com.epam.esm.exceptions.DaoException;
import com.epam.esm.exceptions.IncorrectParameterException;

import java.util.List;

public interface CRDService<T> {

    T getById(long id) throws DaoException, IncorrectParameterException;

    List<T> getAll() throws DaoException;

    void insert(T entity) throws DaoException, IncorrectParameterException;

    void removeById(long id) throws DaoException, IncorrectParameterException;
}
