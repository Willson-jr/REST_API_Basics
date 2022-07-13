package com.epam.esm.service.impl;

import com.epam.esm.AbstractService;
import com.epam.esm.GiftCertificateDao;
import com.epam.esm.GiftCertificateService;
import com.epam.esm.TagDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.DaoException;
import com.epam.esm.exceptions.IncorrectParameterException;
import com.epam.esm.validator.GiftCertificateValidator;
import com.epam.esm.validator.IdentifiableValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.epam.esm.FilterParameters.*;

public class GiftCertificateServiceImpl extends AbstractService<GiftCertificate> implements GiftCertificateService {
    private final GiftCertificateDao giftCertificateDao;
    private final TagDao tagDao;

    @Autowired
    public GiftCertificateServiceImpl(TagDao tagDao, GiftCertificateDao giftCertificateDao) {
        super(giftCertificateDao);
        this.tagDao = tagDao;
        this.giftCertificateDao = giftCertificateDao;

    }

    @Override
    public void insert(GiftCertificate giftCertificate) throws DaoException, IncorrectParameterException {
        LocalDateTime localDateTime = LocalDateTime.now();
        GiftCertificateValidator.validate(giftCertificate);
        giftCertificate.setCreateDate(localDateTime);
        giftCertificate.setLastUpdateDate(localDateTime);
        List<Tag> requestTags = giftCertificate.getTags();
        List<Tag> createdTags = tagDao.getAll();
        saveNewTags(requestTags, createdTags);
        dao.insert(giftCertificate);
    }

    @Override
    public void update(long id, GiftCertificate giftCertificate) throws DaoException, IncorrectParameterException {
        LocalDateTime localDateTime = LocalDateTime.now();
        IdentifiableValidator.validateId(id);
        giftCertificate.setId(id);
        GiftCertificateValidator.validateForUpdate(giftCertificate);
        giftCertificate.setLastUpdateDate(localDateTime);
        List<Tag> requestTags = giftCertificate.getTags();
        List<Tag> createdTags = tagDao.getAll();
        saveNewTags(requestTags, createdTags);
        giftCertificateDao.update(giftCertificate);
    }

    @Override
    public List<Tag> getAssociatedTags(long certificateId) throws DaoException, IncorrectParameterException {
        IdentifiableValidator.validateId(certificateId);
        return giftCertificateDao.getAssociatedTags(certificateId);
    }

    @Override
    public void addAssociatedTags(long id, List<Tag> tags) throws DaoException, IncorrectParameterException {
        IdentifiableValidator.validateId(id);
        GiftCertificateValidator.validateListOfTags(tags);
        List<Tag> createdTags = tagDao.getAll();
        saveNewTags(tags, createdTags);
        giftCertificateDao.addTagsAssociation(id, tags);
    }

    @Override
    public void deleteAssociatedTags(long id, List<Tag> tags) throws DaoException, IncorrectParameterException {
        IdentifiableValidator.validateId(id);
        GiftCertificateValidator.validateListOfTags(tags);
        giftCertificateDao.deleteTagsAssociation(id, tags);
    }

    @Override
    public List<GiftCertificate> doFilter(MultiValueMap<String, String> requestParams) throws DaoException {
        Map<String, String> map = new HashMap<>();
        map.put(NAME, getSingleRequestParameter(requestParams, NAME));
        map.put(TAG_NAME, getSingleRequestParameter(requestParams, TAG_NAME));
        map.put(PART_OF_NAME, getSingleRequestParameter(requestParams, PART_OF_NAME));
        map.put(PART_OF_DESCRIPTION, getSingleRequestParameter(requestParams, PART_OF_DESCRIPTION));
        map.put(PART_OF_TAG_NAME, getSingleRequestParameter(requestParams, PART_OF_TAG_NAME));
        map.put(SORT_BY_NAME, getSingleRequestParameter(requestParams, SORT_BY_NAME));
        map.put(SORT_BY_CREATE_DATE, getSingleRequestParameter(requestParams, SORT_BY_CREATE_DATE));
        map.put(SORT_BY_TAG_NAME, getSingleRequestParameter(requestParams, SORT_BY_TAG_NAME));
        return giftCertificateDao.getWithFilters(map);
    }

    private void saveNewTags(List<Tag> requestTags, List<Tag> createdTags) throws DaoException {
        if (requestTags == null) {
            return;
        }

        for (Tag requestTag : requestTags) {
            boolean isExist = false;
            for (Tag createdTag : createdTags) {
                if (Objects.equals(requestTag.getName(), createdTag.getName())) {
                    isExist = true;
                    break;
                }
            }
            if (!isExist) {
                tagDao.insert(requestTag);
            }
        }


    }
}
