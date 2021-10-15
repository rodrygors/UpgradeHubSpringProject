package com.example.loja.exception;

import com.example.loja.controller.HttpErrorResponse;
import com.example.loja.model.Purchase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionsHandler {
    @ExceptionHandler({CustomerNotFound.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public HttpErrorResponse handleGenericException(CustomerNotFound exception) {
        return new HttpErrorResponse(
                404,
                exception.getMessage(),
                LocalDateTime.now()
        );
    }

    @ExceptionHandler({ProductNotFound.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public HttpErrorResponse handleGenericException(ProductNotFound exception) {
        return new HttpErrorResponse(
                404,
                exception.getMessage(),
                LocalDateTime.now()
        );
    }

    @ExceptionHandler({PurchaseNotFound.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public HttpErrorResponse handleGenericException(PurchaseNotFound exception) {
        return new HttpErrorResponse(
                404,
                exception.getMessage(),
                LocalDateTime.now()
        );
    }
}
