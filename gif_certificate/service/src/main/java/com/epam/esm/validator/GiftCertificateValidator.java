package com.epam.esm.validator;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.IncorrectParameterException;

import java.math.BigDecimal;
import java.util.List;

import static com.epam.esm.exceptions.ExceptionIncorrectParameterMessageCodes.*;
import static com.epam.esm.exceptions.ExceptionIncorrectParameterMessageCodes.BAD_GIFT_CERTIFICATE_DURATION;

public class GiftCertificateValidator {
    private static final int MAX_LENGTH_NAME = 45;
    private static final int MIN_LENGTH_NAME = 3;
    private static final int MAX_LENGTH_DESCRIPTION = 300;
    private static final int MAX_SCALE = 2;
    private static final BigDecimal MIN_PRICE = new BigDecimal("0.01");
    private static final BigDecimal MAX_PRICE = new BigDecimal("999999.99");
    private static final int MAX_DURATION = 366;
    private static final int MIN_DURATION = 1;

    public static void validate(GiftCertificate giftCertificate) throws IncorrectParameterException {
        validateName(giftCertificate.getName());
        validateDescription(giftCertificate.getDescription());
        validatePrice(giftCertificate.getPrice());
        validateDuration(giftCertificate.getDuration());
        validateListOfTags(giftCertificate.getTags());
    }

    public static void validateForUpdate(GiftCertificate giftCertificate) throws IncorrectParameterException {
        if (giftCertificate.getName() != null) {
            validateName(giftCertificate.getName());
        }
        if (giftCertificate.getDescription() != null) {
            validateDescription(giftCertificate.getDescription());
        }
        if (giftCertificate.getPrice() != null) {
            validatePrice(giftCertificate.getPrice());
        }
        if (giftCertificate.getDuration() != 0) {
            validateDuration(giftCertificate.getDuration());
        }
        validateListOfTags(giftCertificate.getTags());
    }

    public static void validateListOfTags(List<Tag> tags) throws IncorrectParameterException {
        if (tags == null) return;
        for (Tag tag : tags) {
            TagValidator.validate(tag);
        }
    }

    private static void validateName(String name) throws IncorrectParameterException {
        if (name == null || name.length() < MIN_LENGTH_NAME || name.length() > MAX_LENGTH_NAME) {
            throw new IncorrectParameterException(BAD_GIFT_CERTIFICATE_NAME);
        }
    }

    private static void validateDescription(String description) throws IncorrectParameterException {
        if (description == null || description.length() > MAX_LENGTH_DESCRIPTION) {
            throw new IncorrectParameterException(BAD_GIFT_CERTIFICATE_DESCRIPTION);
        }
    }

    private static void validatePrice(BigDecimal price) throws IncorrectParameterException {
        if (price == null || price.scale() > MAX_SCALE
                || price.compareTo(MIN_PRICE) < 0 || price.compareTo(MAX_PRICE) > 0) {
            throw new IncorrectParameterException(BAD_GIFT_CERTIFICATE_PRICE);
        }
    }

    private static void validateDuration(int duration) throws IncorrectParameterException {
        if (duration < MIN_DURATION || duration > MAX_DURATION) {
            throw new IncorrectParameterException(BAD_GIFT_CERTIFICATE_DURATION);
        }
    }
}
