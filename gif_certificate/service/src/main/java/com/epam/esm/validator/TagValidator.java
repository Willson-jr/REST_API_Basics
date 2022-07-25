package com.epam.esm.validator;

import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.DaoException;
import com.epam.esm.exceptions.IncorrectParameterException;

import java.util.List;

import static com.epam.esm.exceptions.ExceptionIncorrectParameterMessageCodes.BAD_TAG_NAME;

public class TagValidator {
    private static final int MAX_LENGTH_NAME = 20;
    private static final int MIN_LENGTH_NAME = 3;

    public static void validate(Tag tag) throws IncorrectParameterException {
        validateName(tag.getName());
    }

    public static void validateName(String name) throws IncorrectParameterException {
        if (name.length() < MIN_LENGTH_NAME || name.length() > MAX_LENGTH_NAME) {
            throw new IncorrectParameterException(BAD_TAG_NAME);
        }
    }

//    public static void validateIfAlreadyExist(String newTag) throws DaoException, IncorrectParameterException {
//
//        for (Tag tag : tags) {
//            if (tag.getName().equals(newTag)){
//                throw new IncorrectParameterException(BAD_TAG_NAME);
//            };
//        }
//    }
}
