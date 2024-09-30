package com.app.clinifono.configurationExceptionsTests;

import com.app.clinifono.configuration.exceptions.*;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ApiExeptionsHandler {
    @InjectMocks
    private ApiExceptionHandler apiExceptionHandler;

    @Mock
    private HttpServletRequest request;

    @Mock
    private BindingResult bindingResult;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    void testMethodArgumentNotValidException() {
//        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
//        when(ex.getBindingResult()).thenReturn(bindingResult);
//
//        ResponseEntity<ErrorMessage> response = apiExceptionHandler.MethodArgumentNotValidException(ex, request, bindingResult);
//
//        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
//        assertEquals("Campo(s) Inválidos", response.getBody().getMessage());
//        verify(ex).getBindingResult();
//    }

    @Test
    void testUniqueValueException() {
        UniqueValueException ex = new UniqueValueException("Valor único já existe");

        ResponseEntity<ErrorMessage> response = apiExceptionHandler.UniqueValueException(ex, request);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Valor único já existe", response.getBody().getMessage());
    }

    @Test
    void testEntityNotFoundException() {
        EntityNotFoundException ex = new EntityNotFoundException("Entidade não encontrada");

        ResponseEntity<ErrorMessage> response = apiExceptionHandler.EntityNotFoundException(ex, request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Entidade não encontrada", response.getBody().getMessage());
    }

    @Test
    void testPasswordMissmatchException() {
        PasswordMissmatchException ex = new PasswordMissmatchException("Senha incorreta");

        ResponseEntity<ErrorMessage> response = apiExceptionHandler.PasswordNotEqual(ex, request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Senha incorreta", response.getBody().getMessage());
    }

    @Test
    void testBusinessException() {
        BusinessException ex = new BusinessException("Erro de negócio");

        ResponseEntity<ErrorMessage> response = apiExceptionHandler.BusinessException(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Erro de negócio", response.getBody().getMessage());
    }
}
