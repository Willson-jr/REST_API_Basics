package com.epam.esm.mapper;

import com.epam.esm.entity.Tag;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.esm.entity.TagTableColumnNames.*;


@Component
public class TagRowMapper implements ResultSetExtractor<List<Tag>> {

    @Override
    public List<Tag> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        List<Tag> tags = new ArrayList<>();
        resultSet.next();
        while (!resultSet.isAfterLast()) {
            Tag tag = new Tag();
            tag.setId(resultSet.getLong(ID));
            tag.setName(resultSet.getString(TAG_NAME));
            tags.add(tag);
            resultSet.next();
        }
        return tags;
    }
}
