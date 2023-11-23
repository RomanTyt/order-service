package ru.tyutyakov.orderservice.exception;

public class BusinessException extends RuntimeException{
    private final Error error;

    public BusinessException(Error error){
        super(error.getErrorDescription().replaceAll("%s ", ""));
        this.error = error;
    }

    public BusinessException(Error error, Object... args){
        super(String.format(error.getErrorDescription(), args));
        this.error = error;
    }

    public Error getError()  {
        return error;
    }
}
