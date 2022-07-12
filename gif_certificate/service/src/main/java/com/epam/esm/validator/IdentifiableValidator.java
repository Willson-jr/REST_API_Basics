package com.epam.esm.validator;

import com.epam.esm.exceptions.IncorrectParameterException;

import static com.epam.esm.exceptions.ExceptionIncorrectParameterMessageCodes.BAD_ID;

public class IdentifiableValidator {
    private static final int MIN_ID = 1;

    public static void validateId(long id) throws IncorrectParameterException {
        if (id < MIN_ID) {
            throw new IncorrectParameterException(BAD_ID);
        }
    }
}
