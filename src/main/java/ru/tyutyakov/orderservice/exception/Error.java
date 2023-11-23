package ru.tyutyakov.orderservice.exception;

import org.springframework.http.HttpStatus;

public enum Error {
    ORDER_NOT_FOUND_EXCEPTION("0001", "Заказ %s не найден в БД.", HttpStatus.BAD_REQUEST),
    PRODUCT_EXIST_EXCEPTION("0002", "Товар с таким номером %s уже есть в заказе", HttpStatus.BAD_REQUEST),
    PRODUCT_NOT_FOUND_EXCEPTION("0003", "Товар %s не найден в БД.", HttpStatus.BAD_REQUEST);

    private final String errorCode;
    private final String errorDescription;
    private final HttpStatus httpStatus;

    Error(String errorCode, String errorDescription, HttpStatus httpStatus) {
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
        this.httpStatus = httpStatus;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
