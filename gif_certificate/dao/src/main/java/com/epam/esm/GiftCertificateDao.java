package com.epam.esm;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.DaoException;

import java.util.List;

public interface GiftCertificateDao extends CRUDDao<GiftCertificate>{

    void addTagsAssociation(long certificateId, List<Tag> tags) throws DaoException;

    void deleteTagsAssociation(long certificateId, List<Tag> tags) throws DaoException;

    List<Tag> getAssociatedTags(long certificateId) throws DaoException;
}
