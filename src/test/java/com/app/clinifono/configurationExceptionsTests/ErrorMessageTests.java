package com.app.clinifono.configurationExceptionsTests;

import com.app.clinifono.configuration.exceptions.*;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class ErrorMessageTests {
    @Mock
    private HttpServletRequest request;

    @Mock
    private BindingResult bindingResult;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(request.getRequestURI()).thenReturn("/api/test");
        when(request.getMethod()).thenReturn("POST");
    }

    @Test
    void testErrorMessageWithoutBindingResult() {
        ErrorMessage errorMessage = new ErrorMessage(request, HttpStatus.BAD_REQUEST, "Erro de teste");
        assertEquals("/api/test", errorMessage.getPath());
        assertEquals("POST", errorMessage.getMethod());
        assertEquals(400, errorMessage.getStatus()); // BAD_REQUEST status code
        assertEquals("Bad Request", errorMessage.getStatusMessage());
        assertEquals("Erro de teste", errorMessage.getMessage());
        assertNull(errorMessage.getErrors()); // Verifica que não há erros de validação
    }

    @Test
    void testErrorMessageWithBindingResult() {
        FieldError fieldError = new FieldError("objeto", "campo", "Campo obrigatório");
        when(bindingResult.getFieldErrors()).thenReturn(java.util.Collections.singletonList(fieldError));
        ErrorMessage errorMessage = new ErrorMessage(request, HttpStatus.UNPROCESSABLE_ENTITY, "Erro de validação", bindingResult);
        assertEquals("/api/test", errorMessage.getPath());
        assertEquals("POST", errorMessage.getMethod());
        assertEquals(422, errorMessage.getStatus()); // UNPROCESSABLE_ENTITY status code
        assertEquals("Unprocessable Entity", errorMessage.getStatusMessage());
        assertEquals("Erro de validação", errorMessage.getMessage());
        assertNotNull(errorMessage.getErrors()); // Verifica que existem erros de validação
        Map<String, String> expectedErrors = new HashMap<>();
        expectedErrors.put("campo", "Campo obrigatório");
        assertEquals(expectedErrors, errorMessage.getErrors());
    }

    @Test
    void testAddErrors() {
        FieldError fieldError1 = new FieldError("objeto", "campo1", "Campo obrigatório");
        FieldError fieldError2 = new FieldError("objeto", "campo2", "Formato inválido");
        when(bindingResult.getFieldErrors()).thenReturn(java.util.Arrays.asList(fieldError1, fieldError2));
        ErrorMessage errorMessage = new ErrorMessage(request, HttpStatus.UNPROCESSABLE_ENTITY, "Erro de validação", bindingResult);
        assertNotNull(errorMessage.getErrors());
        Map<String, String> expectedErrors = new HashMap<>();
        expectedErrors.put("campo1", "Campo obrigatório");
        expectedErrors.put("campo2", "Formato inválido");
        assertEquals(expectedErrors, errorMessage.getErrors());
    }
}
