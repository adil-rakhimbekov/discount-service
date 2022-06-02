package kz.adil.discountservice.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class ServiceExceptionHandlerTest {
    /**
     * Method under test: {@link ServiceExceptionHandler#handleValidation(ValidationException)}
     */
    @Test
    void testHandleValidation() {
        // Arrange
        ServiceExceptionHandler serviceExceptionHandler = new ServiceExceptionHandler();
        ValidationException validationException = new ValidationException(1, "An error occurred");

        // Act
        ResponseEntity<Object> actualHandleValidationResult = serviceExceptionHandler.handleValidation(validationException);

        // Assert
        assertTrue(actualHandleValidationResult.hasBody());
        assertTrue(actualHandleValidationResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.BAD_REQUEST, actualHandleValidationResult.getStatusCode());
        assertEquals(1, ((ServiceExceptionHandler.ErrorBody) actualHandleValidationResult.getBody()).getCode());
        assertEquals("An error occurred",
                ((ServiceExceptionHandler.ErrorBody) actualHandleValidationResult.getBody()).getMessage());
    }
}

