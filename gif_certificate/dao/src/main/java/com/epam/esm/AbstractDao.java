package com.epam.esm;

import com.epam.esm.creator.QueryCreator;
import com.epam.esm.exceptions.DaoException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.epam.esm.exceptions.ExceptionDaoMessageCodes.*;

public abstract class AbstractDao<T> {
    protected final JdbcTemplate jdbcTemplate;
    protected final ResultSetExtractor<List<T>> rowMapper;
    protected final QueryCreator queryCreator;

    protected abstract String getTableName();

    protected abstract String getSelectJoiner();

    public AbstractDao(JdbcTemplate jdbcTemplate, ResultSetExtractor<List<T>> rowMapper, QueryCreator queryCreator) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
        this.queryCreator = queryCreator;
    }

    protected List<T> executeQuery(String query, Object... params) {
        return jdbcTemplate.query(query, params, rowMapper);
    }

    protected T executeQueryAsSingleResult(String query, Object... params) {
        List<T> result = executeQuery(query, params);
        if (result.size() == 1) {
            return result.get(0);
        } else {
            return null;
        }
    }

    protected void executeUpdateQuery(String query, Object... params) {
        jdbcTemplate.update(query, params);
    }

    public T getById(long id) throws DaoException {
        try {
            StringBuilder query = new StringBuilder("SELECT * FROM " + getTableName() + getSelectJoiner() + " WHERE ");
            if (Objects.equals(getTableName(), "gift_certificates")) {
                query.append("gc.id=?");
            } else {
                query.append("id=?");
            }
            return executeQueryAsSingleResult(query.toString(), id);
        } catch (DataAccessException e) {
            throw new DaoException(NO_ENTITY_WITH_ID, e);
        }
    }

    public List<T> getAll() throws DaoException {
        try {
            String value = "SELECT * FROM " + getTableName() + getSelectJoiner();
            return executeQuery(value);
        } catch (DataAccessException e) {
            throw new DaoException(NO_ENTITY, e);
        }
    }

    @Transactional
    public void removeById(long id) throws DaoException {
        try {
            if (Objects.equals(getTableName(), "tags")) {
                executeUpdateQuery("DELETE FROM gift_certificates_tags WHERE tag_id=?", id);
            } else {
                executeUpdateQuery("DELETE FROM gift_certificates_tags WHERE gift_certificate_id=?", id);
            }
            executeUpdateQuery("DELETE FROM " + getTableName() + " WHERE id=?", id);
        } catch (DataAccessException e) {
            throw new DaoException(NO_ENTITY_WITH_ID, e);
        }
    }

    public List<T> getWithFilters(Map<String, String> fields) throws DaoException {
        try {
            String query = queryCreator.createGetQuery(fields, getTableName());
            return jdbcTemplate.query(query, rowMapper);
        } catch (DataAccessException e) {
            throw new DaoException(NO_ENTITY_WITH_PARAMETERS, e);
        }
    }
}
