package com.epam.esm;

import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.DaoException;
import org.springframework.util.MultiValueMap;

import java.util.List;

public interface TagService extends CRDService<Tag>{

    List<Tag> doFilter(MultiValueMap<String, String> requestParams) throws DaoException;

    Boolean checkIfExistByName(String name) throws DaoException;

}
