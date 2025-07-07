package com.nnk.springboot.unitaire;

import com.nnk.springboot.config.PasswordConstraintValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PasswordConstraintValidatorTest {

    private PasswordConstraintValidator validator;

    @BeforeEach
    void setUp() {
        validator = new PasswordConstraintValidator();
    }

    @Test
    void testValidPassword() {
        String password = "Abcd1234@";
        assertThat(validator.isValid(password, null)).isTrue();
    }

    @Test
    void testPasswordMissingUppercase() {
        String password = "abcd1234@";
        assertThat(validator.isValid(password, null)).isFalse();
    }

    @Test
    void testPasswordMissingLowercase() {
        String password = "ABCD1234@";
        assertThat(validator.isValid(password, null)).isFalse();
    }

    @Test
    void testPasswordMissingDigit() {
        String password = "Abcdefg@";
        assertThat(validator.isValid(password, null)).isFalse();
    }

    @Test
    void testPasswordMissingSpecialChar() {
        String password = "Abcd1234";
        assertThat(validator.isValid(password, null)).isFalse();
    }

    @Test
    void testPasswordTooShort() {
        String password = "Ab1@";
        assertThat(validator.isValid(password, null)).isFalse();
    }

    @Test
    void testNullPassword() {
        assertThat(validator.isValid(null, null)).isFalse();
    }

    @Test
    void testEmptyPassword() {
        assertThat(validator.isValid("", null)).isFalse();
    }

    @Test
    void testPasswordWithUnsupportedSpecialChar() {
        String password = "Abcd1234#"; // '#' not in @$!%*?&
        assertThat(validator.isValid(password, null)).isFalse();
    }
}
