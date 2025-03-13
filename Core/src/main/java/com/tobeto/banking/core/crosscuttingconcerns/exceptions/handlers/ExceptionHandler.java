package com.tobeto.banking.core.crosscuttingconcerns.exceptions.handlers;

import com.tobeto.banking.core.crosscuttingconcerns.exceptions.httpProblemDetails.ProblemDetails;
import com.tobeto.banking.core.crosscuttingconcerns.exceptions.types.BusinessException;
import com.tobeto.banking.core.crosscuttingconcerns.exceptions.types.ValidationException;
import com.tobeto.banking.core.crosscuttingconcerns.exceptions.types.AuthorizationException;
import com.tobeto.banking.core.crosscuttingconcerns.exceptions.types.NotFoundException;

/**
 * Exception handler sınıfları için soyut arayüz
 */
public abstract class ExceptionHandler {
    public abstract ProblemDetails handleException(Exception exception);
    public abstract ProblemDetails handleBusinessException(BusinessException exception);
    public abstract ProblemDetails handleValidationException(ValidationException exception);
    public abstract ProblemDetails handleAuthorizationException(AuthorizationException exception);
    public abstract ProblemDetails handleNotFoundException(NotFoundException exception);
} 