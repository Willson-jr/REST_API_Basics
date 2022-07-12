package com.epam.esm;

import com.epam.esm.exceptions.DaoException;
import com.epam.esm.exceptions.IncorrectParameterException;
import com.epam.esm.validator.IdentifiableValidator;
import org.springframework.util.MultiValueMap;

import java.util.List;

public abstract class AbstractService<T> implements CRDService<T>{

    protected final CRDDao<T> dao;

    public AbstractService(CRDDao<T> dao) {
        this.dao = dao;
    }

    @Override
    public T getById(long id) throws DaoException, IncorrectParameterException {
        IdentifiableValidator.validateId(id);
        return dao.getById(id);
    }

    @Override
    public List<T> getAll() throws DaoException {
        return dao.getAll();
    }

    @Override
    public void removeById(long id) throws DaoException, IncorrectParameterException {
        IdentifiableValidator.validateId(id);
        dao.removeById(id);
    }

    protected String getSingleRequestParameter(MultiValueMap<String, String> requestParams, String parameter) {
        if (requestParams.containsKey(parameter)) {
            return requestParams.get(parameter).get(0);
        } else {
            return null;
        }
    }
}
