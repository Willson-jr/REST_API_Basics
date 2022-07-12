package com.epam.esm;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.DaoException;
import com.epam.esm.exceptions.IncorrectParameterException;
import org.springframework.util.MultiValueMap;

import java.util.List;

public interface GiftCertificateService extends CRUDService<GiftCertificate> {

    List<Tag> getAssociatedTags(long certificateId) throws DaoException, IncorrectParameterException;

    void addAssociatedTags(long id, List<Tag> tags) throws DaoException, IncorrectParameterException;

    void deleteAssociatedTags(long id, List<Tag> tags) throws DaoException, IncorrectParameterException;

    List<GiftCertificate> doFilter(MultiValueMap<String, String> requestParams) throws DaoException;

}
