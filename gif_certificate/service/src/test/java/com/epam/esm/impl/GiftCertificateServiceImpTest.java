package com.epam.esm.impl;

import com.epam.esm.entity.*;

import com.epam.esm.exceptions.DaoException;
import com.epam.esm.exceptions.IncorrectParameterException;
import com.epam.esm.impl.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
public class GiftCertificateServiceImpTest {

    @Mock
    private GiftCertificateDaoImpl giftCertificateDao = Mockito.mock(GiftCertificateDaoImpl.class);

    @InjectMocks
    private GiftCertificateServiceImpl giftCertificateService;

    private static final Tag TAG_2 = new Tag(2, "tagName3");

    private static final GiftCertificate GIFT_CERTIFICATE_1 =
            new GiftCertificate(1L,
                    "giftCertificate1",
                    "description1",
                    new BigDecimal("10.1"),
                    1,
                    LocalDateTime.parse("2020-08-29T06:12:15.156"),
                    LocalDateTime.parse("2020-08-29T06:12:15.156"),
                    Arrays.asList(new Tag(1, "tagName1"),
                                new Tag(2, "tagName3"),
                                new Tag(3, "tagName5")));

    private static final GiftCertificate GIFT_CERTIFICATE_2 =
            new GiftCertificate(2L,
                    "giftCertificate3",
                    "description3",
                    new BigDecimal("30.3"),
                    3,
                    LocalDateTime.parse("2020-08-29T06:12:15.156"),
                    LocalDateTime.parse("2020-08-29T06:12:15.156"),
                    Collections.singletonList(new Tag(2, "tagName3")));

    private static final GiftCertificate GIFT_CERTIFICATE_3 =
            new GiftCertificate(3L,
                    "giftCertificate2",
                    "description2",
                    new BigDecimal("20.2"),
                    2,
                    LocalDateTime.parse("2020-08-29T06:12:15.156"),
                    LocalDateTime.parse("2020-08-29T06:12:15.156"),
                    Collections.singletonList(new Tag(0, null)));

    private static final String SORT_PARAMETER = "DESC";

    @Test
    void shouldCallGetByIdAndCallStabMethodOnce() throws DaoException, IncorrectParameterException {
        when(giftCertificateDao.getById(GIFT_CERTIFICATE_1.getId())).thenReturn(GIFT_CERTIFICATE_1);
        GiftCertificate actual = giftCertificateService.getById(GIFT_CERTIFICATE_1.getId());
        GiftCertificate expected = GIFT_CERTIFICATE_1;

        verify(giftCertificateDao, times(1)).getById(anyLong());
        assertEquals(actual, expected);
    }

    @Test
    void shouldCallGetAllAndCallStabMethodOnce() throws DaoException {
        List<GiftCertificate> giftCertificateList =
                Arrays.asList(GIFT_CERTIFICATE_1, GIFT_CERTIFICATE_2, GIFT_CERTIFICATE_3);
        when(giftCertificateDao.getAll()).thenReturn(giftCertificateList);
        List<GiftCertificate> actual = giftCertificateService.getAll();
        List<GiftCertificate> expected = giftCertificateList;

        verify(giftCertificateDao, times(1)).getAll();
        assertEquals(actual, expected);
    }

    @Test
    void shouldCallGetAssociatedTagAndCallStabMethodOnce() throws DaoException, IncorrectParameterException {
        when(giftCertificateDao.getAssociatedTags(GIFT_CERTIFICATE_2.getId()))
                .thenReturn(Collections.singletonList(TAG_2));
        List<Tag> actual = giftCertificateService.getAssociatedTags(GIFT_CERTIFICATE_2.getId());
        List<Tag> expected = Collections.singletonList(TAG_2);

        verify(giftCertificateDao, times(1)).getAssociatedTags(GIFT_CERTIFICATE_2.getId());
        assertEquals(expected, actual);
    }

    @Test
    void shouldCallGetWithFiltersAndCallStabMethodOnce() throws DaoException {
        List<GiftCertificate> giftCertificates = Arrays.asList(GIFT_CERTIFICATE_2, GIFT_CERTIFICATE_1);
        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("tag_name", TAG_2.getName());
        requestParams.add("sortByName", SORT_PARAMETER);

        Map<String, String> parameters = new HashMap<>();
        parameters.put("sortByName", SORT_PARAMETER);
        parameters.put("partOfDescription", null);
        parameters.put("partOfTagName", null);
        parameters.put("tag_name", TAG_2.getName());
        parameters.put("sortByTagName", null);
        parameters.put("sortByCreateDate", null);
        parameters.put("name", null);
        parameters.put("partOfName", null);
        when(giftCertificateDao.getWithFilters(parameters)).thenReturn(giftCertificates);
        List<GiftCertificate> actual = giftCertificateService.doFilter(requestParams);
        List<GiftCertificate> expected = giftCertificates;

        verify(giftCertificateDao, times(1)).getWithFilters(parameters);
        assertEquals(expected, actual);
    }
}
