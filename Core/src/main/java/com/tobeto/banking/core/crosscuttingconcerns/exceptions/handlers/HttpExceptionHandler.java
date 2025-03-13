package com.tobeto.banking.core.crosscuttingconcerns.exceptions.handlers;

import com.tobeto.banking.core.crosscuttingconcerns.exceptions.httpProblemDetails.*;
import com.tobeto.banking.core.crosscuttingconcerns.exceptions.types.AuthorizationException;
import com.tobeto.banking.core.crosscuttingconcerns.exceptions.types.BusinessException;
import com.tobeto.banking.core.crosscuttingconcerns.exceptions.types.NotFoundException;
import com.tobeto.banking.core.crosscuttingconcerns.exceptions.types.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * HTTP isteklerinde oluşan hataları yakalayan ve uygun yanıtları üreten handler sınıfı
 */
@RestControllerAdvice
public class HttpExceptionHandler extends com.tobeto.banking.core.crosscuttingconcerns.exceptions.handlers.ExceptionHandler {
    
    @Override
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProblemDetails handleBusinessException(BusinessException exception) {
        return new BusinessProblemDetails(exception.getMessage());
    }
    
    @Override
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProblemDetails handleValidationException(ValidationException exception) {
        return new ValidationProblemDetails(exception.getMessage(), exception.getValidationErrors());
    }
    
    @Override
    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ProblemDetails handleAuthorizationException(AuthorizationException exception) {
        return new AuthorizationProblemDetails(exception.getMessage());
    }
    
    @Override
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ProblemDetails handleNotFoundException(NotFoundException exception) {
        return new NotFoundProblemDetails(exception.getMessage());
    }
    
    @Override
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ProblemDetails handleException(Exception exception) {
        return new InternalServerErrorProblemDetails(exception.getMessage());
    }
    
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProblemDetails handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        Map<String, String> validationErrors = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error -> {
            validationErrors.put(error.getField(), error.getDefaultMessage());
        });
        
        return new ValidationProblemDetails(
                "Validation.Exception",
                validationErrors
        );
    }
} 