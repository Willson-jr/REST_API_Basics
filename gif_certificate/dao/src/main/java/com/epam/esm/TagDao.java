package com.epam.esm;

import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.DaoException;

import java.util.List;

public interface TagDao extends CRDDao<Tag> {

    List<Tag> getByName(String name) throws DaoException;

}
