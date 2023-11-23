package ru.tyutyakov.orderservice.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AppExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<AppError> handleException(BusinessException e) {
        return new ResponseEntity<>(new AppError(e.getError().getErrorCode(), e.getMessage()), e.getError().getHttpStatus());
    }
}
