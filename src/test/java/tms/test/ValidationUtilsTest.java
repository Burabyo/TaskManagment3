package tms.test;

import org.junit.jupiter.api.Test;
import tms.utils.ValidationUtils;

import static org.junit.jupiter.api.Assertions.*;

class ValidationUtilsTest {

    @Test
    void testValidEmail() {
        assertTrue(ValidationUtils.isValidEmail("user@example.com"));
    }

    @Test
    void testInvalidEmail() {
        assertFalse(ValidationUtils.isValidEmail("invalid-email"));
    }

    @Test
    void testValidProjectId() {
        assertTrue(ValidationUtils.isValidProjectId("P1234"));
    }

    @Test
    void testInvalidProjectId() {
        assertFalse(ValidationUtils.isValidProjectId("XYZ"));
    }
}
