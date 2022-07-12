package com.epam.esm.validator;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.IncorrectParameterException;
import com.epam.esm.validator.GiftCertificateValidator;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GiftCertificateValidatorTest {
    private static final List<Tag> INCORRECT_TAGS = Arrays.asList(new Tag(1L,"1"), new Tag(2L,"2"));
    private static final List<Tag> CORRECT_TAGS = Arrays.asList(new Tag(3L,"tagName1"), new Tag(4L,"tagName2"));

    private static final GiftCertificate INCORRECT_GIFT_CERTIFICATE = new GiftCertificate(1L, " ",
            " ", new BigDecimal("10.115"), 1, LocalDateTime.parse("2020-08-29T06:12:15.156"),
            LocalDateTime.parse("2020-08-29T06:12:15.156"), INCORRECT_TAGS);
    private static final GiftCertificate CORRECT_GIFT_CERTIFICATE = new GiftCertificate(1L, "giftCertificate",
            "description", new BigDecimal("10.15"), 1, LocalDateTime.parse("2020-08-29T06:12:15.156"),
            LocalDateTime.parse("2020-08-29T06:12:15.156"), CORRECT_TAGS);

    @Test
    void testValidate_incorrectData() {
        assertThrows(IncorrectParameterException.class, () ->
                GiftCertificateValidator.validate(INCORRECT_GIFT_CERTIFICATE));
    }

    @Test
    void testValidate_correctData() {
        assertDoesNotThrow(() -> GiftCertificateValidator.validate(CORRECT_GIFT_CERTIFICATE));
    }

    @Test
    void testValidateForUpdate_incorrectData() {
        assertThrows(IncorrectParameterException.class, () ->
                GiftCertificateValidator.validateForUpdate(INCORRECT_GIFT_CERTIFICATE));
    }

    @Test
    void testValidateForUpdate_correctData() {
        assertDoesNotThrow(() -> GiftCertificateValidator.validateForUpdate(CORRECT_GIFT_CERTIFICATE));
    }

    @Test
    void testValidateListOfTags_incorrectData() {
        assertThrows(IncorrectParameterException.class, () ->
                GiftCertificateValidator.validateListOfTags(INCORRECT_TAGS));
    }

    @Test
    void testValidateListOfTags_correctData() {
        assertDoesNotThrow(() -> GiftCertificateValidator.validateListOfTags(CORRECT_TAGS));
    }
}
