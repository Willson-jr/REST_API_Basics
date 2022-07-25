package com.epam.esm.service.impl;

import com.epam.esm.AbstractService;
import com.epam.esm.CRDDao;
import com.epam.esm.TagDao;
import com.epam.esm.TagService;
import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.DaoException;
import com.epam.esm.exceptions.IncorrectParameterException;
import com.epam.esm.validator.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epam.esm.FilterParameters.*;
@Service
public class TagServiceImpl extends AbstractService<Tag> implements TagService {
    private final TagDao tagDao;

    @Autowired
    public TagServiceImpl(CRDDao<Tag> dao, TagDao tagDao) {
        super(dao);
        this.tagDao = tagDao;
    }

    @Override
    public void insert(Tag tag) throws DaoException, IncorrectParameterException {
        TagValidator.validateName(tag.getName());
        checkIfExistByName(tag.getName());
        dao.insert(tag);
    }

    @Override
    public List<Tag> doFilter(MultiValueMap<String, String> requestParams) throws DaoException {
        Map<String, String> map = new HashMap<>();
        map.put(TAG_NAME, getSingleRequestParameter(requestParams, TAG_NAME));
        map.put(PART_OF_TAG_NAME, getSingleRequestParameter(requestParams, PART_OF_TAG_NAME));
        map.put(SORT_BY_TAG_NAME, getSingleRequestParameter(requestParams, SORT_BY_TAG_NAME));
        return tagDao.getWithFilters(map);
    }

    @Override
    public Boolean checkIfExistByName(String name) throws DaoException{
        if (tagDao.getByName(name).size() > 0) {
            return true;
        } else {
            return false;
        }
    }
}
