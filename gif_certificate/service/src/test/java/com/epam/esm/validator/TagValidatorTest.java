package com.epam.esm.validator;

import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.IncorrectParameterException;
import com.epam.esm.validator.TagValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TagValidatorTest {
    private static final String INCORRECT_NAME = "yo";
    private static final String CORRECT_NAME = "tagName4";
    private static final Tag INCORRECT_TAG = new Tag(1, INCORRECT_NAME);
    private static final Tag CORRECT_TAG = new Tag(5, CORRECT_NAME);

    @Test
    void testValidate_incorrectData() {
        assertThrows(IncorrectParameterException.class, () -> TagValidator.validate(INCORRECT_TAG));
    }

    @Test
    void testValidate_correctData() {
        assertDoesNotThrow(() -> TagValidator.validate(CORRECT_TAG));
    }

    @Test
    void testValidateName_incorrectData() {
        assertThrows(IncorrectParameterException.class, () -> TagValidator.validateName(INCORRECT_NAME));
    }

    @Test
    void testValidateName_correctData() {
        assertDoesNotThrow(() -> TagValidator.validateName(CORRECT_NAME));
    }
}
