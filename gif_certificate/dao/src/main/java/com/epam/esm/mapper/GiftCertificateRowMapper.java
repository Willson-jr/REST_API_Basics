package com.epam.esm.mapper;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.esm.entity.GiftCertificateTableColumnNames.*;
import static com.epam.esm.entity.TagTableColumnNames.TAG_NAME;

@Component
public class GiftCertificateRowMapper implements ResultSetExtractor<List<GiftCertificate>> {
    private static final String TAG_ID = "tag_id";

    @Override
    public List<GiftCertificate> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        resultSet.next();
        while (!resultSet.isAfterLast()) {
            GiftCertificate giftCertificate = new GiftCertificate();
            giftCertificate.setId(resultSet.getLong(ID));
            giftCertificate.setName(resultSet.getString(NAME));
            giftCertificate.setDescription(resultSet.getString(DESCRIPTION));
            giftCertificate.setPrice(resultSet.getBigDecimal(PRICE));
            giftCertificate.setDuration(resultSet.getInt(DURATION));
            giftCertificate.setCreateDate(resultSet.getTimestamp(CREATE_DATE).toLocalDateTime());
            giftCertificate.setLastUpdateDate(resultSet.getTimestamp(LAST_UPDATE_DATE).toLocalDateTime());

            List<Tag> tags = new ArrayList<>();
            while (!resultSet.isAfterLast() && resultSet.getInt(ID) == giftCertificate.getId()) {
                long tagId = resultSet.getLong(TAG_ID);
                String tagName = resultSet.getString(TAG_NAME);
                tags.add(new Tag(tagId, tagName));
                resultSet.next();
            }
            giftCertificate.setTags(tags);
            giftCertificates.add(giftCertificate);
        }
        return giftCertificates;
    }
}
