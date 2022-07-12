package com.epam.esm;

import com.epam.esm.exceptions.DaoException;

import java.util.List;
import java.util.Map;

public interface CRDDao<T> {

    T getById(long id) throws DaoException;

    List<T> getAll() throws DaoException;

    void insert(T item) throws DaoException;

    void removeById(long id) throws DaoException;

    List<T> getWithFilters(Map<String, String> fields) throws DaoException;
}
