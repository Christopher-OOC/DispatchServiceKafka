package com.appdevelopersblog.ws.ProductsMicroservice.error;

public class NotTryableException extends RuntimeException {

    public NotTryableException(String message) {
        super(message);
    }

    public NotTryableException(Throwable cause) {
        super(cause);
    }
}
